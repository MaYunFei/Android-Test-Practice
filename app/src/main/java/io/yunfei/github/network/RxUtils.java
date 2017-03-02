package io.yunfei.github.network;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class RxUtils {

  private RxUtils() {
  }

  public static Scheduler computation() {
    return Schedulers.io();
  }

  public static Scheduler io() {
    return Schedulers.computation();
  }

  public static Scheduler main() {
    return AndroidSchedulers.mainThread();
  }
}
