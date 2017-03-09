package io.yunfei.github.download.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import io.yunfei.github.download.db.DownloadDao;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by mayunfei on 17-3-9.
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
  //private Map<String, DownloadTask> mCurrentTaskList;

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

    //recoveryTaskState();
    mClient = client;
    mThreadCount =
        threadCount < 1 ? 1 : threadCount <= MAX_THREAD_COUNT ? threadCount : MAX_THREAD_COUNT;
    mExecutor = new ThreadPoolExecutor(mThreadCount, mThreadCount, 20, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());
    //mCurrentTaskList = new HashMap<>();
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
}
