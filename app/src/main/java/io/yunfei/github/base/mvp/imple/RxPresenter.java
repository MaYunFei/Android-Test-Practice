package io.yunfei.github.base.mvp.imple;

import android.support.annotation.NonNull;
import io.yunfei.github.base.mvp.core.Presenter;
import io.yunfei.github.base.mvp.core.View;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yunfei on 2017/2/20.
 * email mayunfei6@gmail.com
 */

public class RxPresenter<V extends View> implements Presenter<V> {
  private V mV;
  private CompositeSubscription mCompositeSubscription;

  @Override public void attachView(V view) {
    mV = view;
  }

  protected V getView() {
    return mV;
  }

  protected void addSuscription(@NonNull Subscription s) {
    if (mCompositeSubscription == null) {
      mCompositeSubscription = new CompositeSubscription();
    }

    mCompositeSubscription.add(s);
  }

  @Override public void detachView() {
    mV = null;
    if (mCompositeSubscription != null) {
      mCompositeSubscription.unsubscribe();
      mCompositeSubscription = null;
    }
  }
}
