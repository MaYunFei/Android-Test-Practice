package io.yunfei.github.dagger;

/**
 * Created by yunfei on 2017/2/27.
 * email mayunfei6@gmail.com
 */

public class ComponentHolder {
  private static AppComponent sAppComponent;

  public static void setAppComponent(AppComponent appComponent) {
    sAppComponent = appComponent;
  }

  public static AppComponent getAppComponent() {
    return sAppComponent;
  }
}
