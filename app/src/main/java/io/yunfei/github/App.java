package io.yunfei.github;

import android.app.Application;
import io.yunfei.github.dagger.AppComponent;
import io.yunfei.github.dagger.AppModule;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.dagger.DaggerAppComponent;
import io.yunfei.github.download.manager.DownloadManager;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class App extends Application {

  private static App instance;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initDagger();
    initDownloader();
  }

  private void initDownloader() {
    DownloadManager.getInstance().init(this,15);
  }

  private void initDagger() {
    AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    ComponentHolder.setAppComponent(appComponent);
  }

  public static App getInstance() {
    return instance;
  }
}
