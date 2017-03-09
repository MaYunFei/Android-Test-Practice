package io.yunfei.github.download.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import io.yunfei.github.download.db.DownloadDao;
import io.yunfei.github.download.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mayunfei on 17-3-9.
 */

public class DownloadTask implements Runnable {

  public DownloadTask(DownloadBundle downloadBundle) {
    this.downloadBundle = downloadBundle;
  }

  private OkHttpClient mClient;

  private DownloadDao mDownloadDao;

  private DownloadTaskListener mListener;

  private DownloadBundle downloadBundle;

  private Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
      int code = msg.what;
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
    InputStream inputStream = null;
    BufferedInputStream bis = null;
    RandomAccessFile tempFile = null;
    String filePath = TextUtils.isEmpty(downloadBundle.getFilePath()) ? FileUtils.getDefaultFilePath() : downloadBundle.getFilePath();
    List<TaskEntity> mTaskQueue = downloadBundle.getMTaskQueue();
    downloadBundle.setStatus(io.yunfei.github.downloadmanager.download.TaskStatus.TASK_STATUS_CONNECTING);
    //mDownloadDao.insertDownLoadBundle()
    try {

      for (TaskEntity mTaskEntity:mTaskQueue){
        String fileName = TextUtils.isEmpty(mTaskEntity.getFileName()) ? FileUtils.getFileNameFromUrl(mTaskEntity.getUrl()) : mTaskEntity.getFileName();
        mTaskEntity.setFileName(fileName);
        mTaskEntity.setFilePath(filePath);
        tempFile = new RandomAccessFile(new File(filePath, fileName), "rwd");

        mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_CONNECTING);
        handler.sendEmptyMessage(TaskStatus.TASK_STATUS_CONNECTING);
        //mDownloadDao.update(mTaskEntity);

        long completedSize = mTaskEntity.getCompletedSize();
        Request request = new Request.Builder().url(mTaskEntity.getUrl()).header("RANGE", "bytes=" + completedSize + "-").build();

        //if (tempFile.length() == 0) {
        //  completedSize = 0;
        //}
        //tempFile.seek(completedSize);
        //
        //Response response = mClient.newCall(request).execute();
      }


    }catch (FileNotFoundException e){

    }

  }

  public void setmClient(OkHttpClient mClient) {
    this.mClient = mClient;
  }

  public void setmDownloadDao(DownloadDao mDownloadDao) {
    this.mDownloadDao = mDownloadDao;
  }

  public void setmListener(DownloadTaskListener mListener) {
    this.mListener = mListener;
  }

  public void setDownloadBundle(DownloadBundle downloadBundle) {
    this.downloadBundle = downloadBundle;
  }
}
