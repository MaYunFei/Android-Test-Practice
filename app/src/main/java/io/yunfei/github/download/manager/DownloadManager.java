package io.yunfei.github.download.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import io.yunfei.github.download.db.DownloadDao;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by mayunfei on 17-3-9.
 * 功能 简单的
 */

public class DownloadManager {
  private static final int MAX_THREAD_COUNT = 15;

  // manager instance
  private static DownloadManager mInstance;

  // quess
  private LinkedBlockingQueue<Runnable> mQueue;

  // download database dao
  private DownloadDao mDownloadDao;

  // ok http
  private OkHttpClient mClient;

  // ThreadPoolExecutor
  private ThreadPoolExecutor mExecutor;

  // the thread count
  private int mThreadCount = 1;

  // task list
  private Map<String, DownloadTask> mCurrentTaskList;

  private DownloadManager() {

  }

  public static synchronized DownloadManager getInstance() {
    if (mInstance == null) {
      mInstance = new DownloadManager();
    }
    return mInstance;
  }

  /**
   * @param context Application
   */
  public void init(@NonNull Context context) {
    init(context, getAppropriateThreadCount());
  }

  /**
   * @param context Application
   * @param threadCount the max download count
   */
  public void init(@NonNull Context context, int threadCount) {
    init(context, threadCount, getOkHttpClient());
  }

  /**
   * @param context Application
   * @param threadCount the max download count
   * @param client okhttp client
   */

  public void init(@NonNull Context context, int threadCount, @NonNull OkHttpClient client) {
    mDownloadDao = new DownloadDao(context);
    //初始化状态
    //recoveryTaskState();
    mClient = client;
    mThreadCount =
        threadCount < 1 ? 1 : threadCount <= MAX_THREAD_COUNT ? threadCount : MAX_THREAD_COUNT;
    mExecutor = new ThreadPoolExecutor(mThreadCount, mThreadCount, 20, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());
    mCurrentTaskList = new HashMap<String, DownloadTask>();
    mQueue = (LinkedBlockingQueue<Runnable>) mExecutor.getQueue();
  }

  /**
   * generate default client
   */
  private OkHttpClient getOkHttpClient() {
    return new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
  }

  /**
   * @return generate the appropriate thread count.
   */
  private int getAppropriateThreadCount() {
    return Runtime.getRuntime().availableProcessors() * 2 + 1;
  }

  /**
   * add task
   */
  public void addTask(@NonNull DownloadTask task) {

    DownloadBundle downloadBundle = task.getDownloadBundle();
    if (downloadBundle != null
        && downloadBundle.getStatus() != TaskStatus.TASK_STATUS_DOWNLOADING) { // 正在下载 已完成
      //配置任务
      task.setDownloadDao(mDownloadDao);
      task.setClient(mClient);
      mCurrentTaskList.put(downloadBundle.getUnique_string(), task);
      if (!mQueue.contains(task)) {
        mExecutor.execute(task);
      }

      if (mExecutor.getTaskCount() > mThreadCount) {
        task.queue();
      }
    }
  }

  /**
   * @return task
   */
  public DownloadTask getTask(@NonNull String unique) {
    DownloadTask currTask = mCurrentTaskList.get(unique);
    if (currTask == null) {
      DownloadBundle downloadBundle = mDownloadDao.queryDownLoadBundle(unique);
      if (downloadBundle != null) {
        currTask = new DownloadTask(downloadBundle);
        int status = downloadBundle.getStatus();
        if (status != TaskStatus.TASK_STATUS_FINISH) {
          mCurrentTaskList.put(unique, currTask);
        }
      }
    }
    return currTask;
  }

  public void pauseTask(@NonNull DownloadTask downloadTask) {
    if (mQueue.contains(downloadTask)) {
      mQueue.remove(downloadTask);
    }
    downloadTask.pause();
  }

  public void resumeTask(@NonNull DownloadTask downloadTask) {
    addTask(downloadTask);
  }
}
