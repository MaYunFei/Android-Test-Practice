package io.yunfei.github;

import android.app.Application;
import io.yunfei.github.dagger.AppComponent;
import io.yunfei.github.dagger.AppModule;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.dagger.DaggerAppComponent;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class App extends Application {
  @Override public void onCreate() {
    super.onCreate();
    initDagger();
  }

  private void initDagger() {
    AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    ComponentHolder.setAppComponent(appComponent);
  }
}
