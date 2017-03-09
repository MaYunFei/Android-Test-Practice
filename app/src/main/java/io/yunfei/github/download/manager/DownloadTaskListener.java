package io.yunfei.github.download.manager;

import io.yunfei.github.downloadmanager.download.*;

/**
 * Created by mayunfei on 17-3-9.
 */

public interface DownloadTaskListener {
  void onQueue(DownloadTask downloadTask);

  /**
   * connecting
   */
  void onConnecting(DownloadTask downloadTask);

  /**
   * downloading
   */
  void onStart(DownloadTask downloadTask);

  /**
   * pauseTask
   */
  void onPause(DownloadTask downloadTask);

  /**
   * cancel
   */
  void onCancel(DownloadTask downloadTask);

  /**
   * success
   */
  void onFinish(DownloadTask downloadTask);

  /**
   * failure
   */
  void onError(DownloadTask downloadTask, int code);
}
