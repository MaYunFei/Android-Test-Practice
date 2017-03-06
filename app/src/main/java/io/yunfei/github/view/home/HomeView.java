package io.yunfei.github.view.home;

import io.yunfei.github.base.mvp.core.View;
import io.yunfei.github.entity.DayEntity;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public interface HomeView extends View{
  void showData(DayEntity dayEntity);
  void showError(Throwable throwable);
}
