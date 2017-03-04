package io.yunfei.github.network;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class ResponseUtils {

  public static <T extends BaseResponse<K>,K> Observable.Transformer<T, K> handleServerError() {
    return new Observable.Transformer<T, K>() {
      @Override public Observable<K> call(Observable<T> tObservable) {
        return tObservable.flatMap(new Func1<T, Observable<K>>() {
          @Override public Observable<K> call(T t) {
            return checkServerObservable(t);
          }
        });
      }
    };
  }

  /**
   * 检查返回值 是否是服务器异常
   */
  private static <T extends BaseResponse<K>, K> Observable<K> checkServerObservable(
      final T response) {
    return Observable.create(new Observable.OnSubscribe<K>() {
      @Override public void call(Subscriber<? super K> subscriber) {
        if (!subscriber.isUnsubscribed()) {
          if (response.error) {
            subscriber.onError(new ServerException());
          } else {
            subscriber.onNext(response.results);
            subscriber.onCompleted();
          }
        }
      }
    });
  }
}
