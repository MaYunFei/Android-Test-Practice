package io.yunfei.github.view.home;

import io.yunfei.github.entity.DayEntity;

/**
 * Created by yunfei on 17-3-4.
 */

public class HomeViewTest implements HomeView {
  @Override public void showData(DayEntity dayEntity) {
    System.out.print("HomeView showData");
  }

  @Override public void showError(Throwable throwable) {
    System.out.print("HomeView showError");
  }
}
