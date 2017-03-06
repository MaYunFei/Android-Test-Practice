package io.yunfei.github.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.yunfei.github.R;
import io.yunfei.github.base.BaseToolbarActivity;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.entity.DayEntity;
import javax.inject.Inject;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public class HomeActivity extends BaseToolbarActivity implements HomeView {

  @Inject HomePresenter mHomePresenter;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Home");
    ComponentHolder.getAppComponent().inject(this);
    mHomePresenter.attachView(this);
    mHomePresenter.getData();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mHomePresenter.detachView();
  }

  @Override public void showData(DayEntity dayEntity) {
    
  }

  @Override public void showError(Throwable throwable) {


  }
}
