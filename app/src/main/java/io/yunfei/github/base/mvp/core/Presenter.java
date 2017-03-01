package io.yunfei.github.base.mvp.core;

/**
 * Created by yunfei on 2017/2/20.
 * email mayunfei6@gmail.com
 */

public interface Presenter<V extends View> {
  //加载视图
  void attachView(V view);

  //销毁视图
  void detachView();
}
