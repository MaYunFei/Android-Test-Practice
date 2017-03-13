package io.yunfei.github.download.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import io.yunfei.github.BuildConfig;
import io.yunfei.github.download.db.DownloadDao;
import io.yunfei.github.download.parser.TaskParser;
import io.yunfei.github.download.utils.FileUtils;
import io.yunfei.github.download.utils.IOUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by mayunfei on 17-3-9.
 */

public class DownloadTask implements Runnable {

  private static final String TAG = "DownloadTask";

  public DownloadTask(DownloadBundle mDownloadBundle) {
    this.mDownloadBundle = mDownloadBundle;
  }

  private OkHttpClient mClient;

  private DownloadDao mDownloadDao;

  private DownloadTaskListener mListener;

  private DownloadBundle mDownloadBundle;

  private Handler handler = new Handler(Looper.getMainLooper()) {
    @Override public void handleMessage(Message msg) {
      int code = msg.what;
      if (mListener == null) return;
      switch (code) {
        case TaskStatus.TASK_STATUS_QUEUE:
          mListener.onQueue(DownloadTask.this);
          break;
        case TaskStatus.TASK_STATUS_CONNECTING:
          mListener.onConnecting(DownloadTask.this);
          break;
        case TaskStatus.TASK_STATUS_DOWNLOADING:
          mListener.onStart(DownloadTask.this);
          break;
        case TaskStatus.TASK_STATUS_PAUSE:
          mListener.onPause(DownloadTask.this);
          break;
        case TaskStatus.TASK_STATUS_CANCEL:
          mListener.onCancel(DownloadTask.this);
          break;
        case TaskStatus.TASK_STATUS_REQUEST_ERROR:
          mListener.onError(DownloadTask.this, TaskStatus.TASK_STATUS_REQUEST_ERROR);
          break;
        case TaskStatus.TASK_STATUS_STORAGE_ERROR:
          mListener.onError(DownloadTask.this, TaskStatus.TASK_STATUS_STORAGE_ERROR);
          break;
        case TaskStatus.TASK_STATUS_FINISH:
          mListener.onFinish(DownloadTask.this);
          break;
      }
    }
  };

  @Override public void run() {

    if (mDownloadBundle.getTotalSize() <= 0) {
      try {

        for (TaskParser taskParser : mDownloadBundle.getTaskParsers()) {
          taskParser.setOkHttpClient(mClient);
          taskParser.parse();
          List<TaskEntity> taskList = taskParser.getTaskList();
          mDownloadBundle.getTaskQueue().addAll(taskList);
        }

        mDownloadBundle.setTotalSize(mDownloadBundle.getTaskQueue().size());
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }

    String filePath =
        TextUtils.isEmpty(mDownloadBundle.getFilePath()) ? FileUtils.getDefaultFilePath()
            : mDownloadBundle.getFilePath();
    List<TaskEntity> mTaskQueue = mDownloadBundle.getTaskQueue();

    if (mTaskQueue.size() <= 0) {
      //TODO 参数错误抛到外部
      if (BuildConfig.DEBUG) {
        Log.e(TAG, "参数异常");
      }
    }
    DownloadBundle downloadBundle =
        mDownloadDao.queryDownLoadBundle(mDownloadBundle.getUnique_string());
    if (downloadBundle == null) {
      //插入数据库
      mDownloadDao.insertDownLoadBundle(mDownloadBundle);
    }
    //更新状态 更新数据库
    mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_CONNECTING);
    handler.sendEmptyMessage(TaskStatus.TASK_STATUS_CONNECTING);
    mDownloadDao.updateDownLoadBundle(mDownloadBundle);
    long totalSize = mDownloadBundle.getTotalSize();

    long index = mDownloadBundle.getCompletedSize();

    for (int i = (int) index; i < totalSize; i++) {
      TaskEntity mTaskEntity = mTaskQueue.get(i);
      InputStream inputStream = null;
      BufferedInputStream bis = null;
      RandomAccessFile tempFile = null;
      try {
        String fileName =
            TextUtils.isEmpty(mTaskEntity.getFileName()) ? FileUtils.getFileNameFromUrl(
                mTaskEntity.getUrl()) : mTaskEntity.getFileName();
        mTaskEntity.setFileName(fileName);
        mTaskEntity.setFilePath(filePath);
        tempFile = new RandomAccessFile(new File(filePath, fileName), "rwd");
        //开始下载
        mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_CONNECTING);
        mDownloadDao.updateTaskEntity(mTaskEntity);

        long completedSize = mTaskEntity.getCompletedSize();
        Request request = new Request.Builder().url(mTaskEntity.getUrl())
            .header("RANGE", "bytes=" + completedSize + "-")
            .build();

        if (tempFile.length() == 0) {
          completedSize = 0;
        }
        tempFile.seek(completedSize);
        Response response = mClient.newCall(request).execute();

        if (response.isSuccessful()) {
          ResponseBody responseBody = response.body();
          if (responseBody != null) {
            mTaskEntity.setTotalSize(responseBody.contentLength());

            mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_DOWNLOADING);
            mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_DOWNLOADING);
            handler.sendEmptyMessage(TaskStatus.TASK_STATUS_DOWNLOADING);

            double updateSize = mTaskEntity.getTotalSize() / 100;
            inputStream = responseBody.byteStream();
            bis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int length;
            int buffOffset = 0;
            while ((length = bis.read(buffer)) > 0
                && mDownloadBundle.getStatus() != TaskStatus.TASK_STATUS_CANCEL
                && mDownloadBundle.getStatus() != TaskStatus.TASK_STATUS_PAUSE) {
              tempFile.write(buffer, 0, length);
              completedSize += length;
              buffOffset += length;
              mTaskEntity.setCompletedSize(completedSize);
              // 避免一直调用数据库
              if (buffOffset >= updateSize) {
                buffOffset = 0;
                mDownloadDao.updateTaskEntity(mTaskEntity);
                //handler.sendEmptyMessage(TaskStatus.TASK_STATUS_DOWNLOADING);
              }
              /**
               * 一个子任务下载完成
               */
              if (completedSize == mTaskEntity.getTotalSize()) {
                mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_FINISH);
                mDownloadDao.updateTaskEntity(mTaskEntity);
                mDownloadBundle.setCompletedSize(i + 1);
                mDownloadDao.updateDownLoadBundle(mDownloadBundle);
                handler.sendEmptyMessage(TaskStatus.TASK_STATUS_DOWNLOADING);
                if (i == totalSize - 1) { //最后一个结束
                  mDownloadBundle.setCompletedSize(totalSize);
                  handler.sendEmptyMessage(TaskStatus.TASK_STATUS_DOWNLOADING);
                  mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_FINISH);
                  mDownloadDao.updateDownLoadBundle(mDownloadBundle);
                  handler.sendEmptyMessage(TaskStatus.TASK_STATUS_FINISH);
                }
              }
            } //While

            if (mDownloadBundle.getStatus() == TaskStatus.TASK_STATUS_CANCEL
                || mDownloadBundle.getStatus() == TaskStatus.TASK_STATUS_PAUSE) {
              Log.e(TAG, "PAUSE OR CANCEL");
              break;
            }
          }
        } else {
          mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_REQUEST_ERROR);
          mDownloadDao.updateTaskEntity(mTaskEntity);
          mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_REQUEST_ERROR);
          mDownloadDao.updateDownLoadBundle(mDownloadBundle);
          handler.sendEmptyMessage(TaskStatus.TASK_STATUS_REQUEST_ERROR);
          return;
        }
      } catch (FileNotFoundException e) {
        mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_STORAGE_ERROR);
        mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_STORAGE_ERROR);
        mDownloadDao.updateTaskEntity(mTaskEntity);
        mDownloadDao.updateDownLoadBundle(mDownloadBundle);
        handler.sendEmptyMessage(TaskStatus.TASK_STATUS_STORAGE_ERROR);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        IOUtils.close(bis, inputStream, tempFile);
      }
    } //for 循环
  }

  void queue() {
    mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_QUEUE);
    handler.sendEmptyMessage(TaskStatus.TASK_STATUS_QUEUE);
  }

  public void setClient(OkHttpClient mClient) {
    this.mClient = mClient;
  }

  public void setDownloadDao(DownloadDao mDownloadDao) {
    this.mDownloadDao = mDownloadDao;
  }

  public void setListener(DownloadTaskListener mListener) {
    this.mListener = mListener;
  }

  public void setDownloadBundle(DownloadBundle mDownloadBundle) {
    this.mDownloadBundle = mDownloadBundle;
  }

  public DownloadBundle getDownloadBundle() {
    return mDownloadBundle;
  }

  public void pause() {
    mDownloadBundle.setStatus(TaskStatus.TASK_STATUS_PAUSE);
    mDownloadDao.updateDownLoadBundle(mDownloadBundle);
    handler.sendEmptyMessage(TaskStatus.TASK_STATUS_PAUSE);
  }
}
