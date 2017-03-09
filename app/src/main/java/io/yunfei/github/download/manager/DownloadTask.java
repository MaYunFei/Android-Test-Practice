package io.yunfei.github.download.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.yunfei.github.download.db.DownloadDao;
import okhttp3.OkHttpClient;

/**
 * Created by mayunfei on 17-3-9.
 */

public class DownloadTask implements Runnable {

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
