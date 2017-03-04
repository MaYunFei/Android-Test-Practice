package io.yunfei.github.view.home;

import io.yunfei.github.base.mvp.imple.RxPresenter;
import io.yunfei.github.entity.DayEntity;
import io.yunfei.github.network.BaseResponse;
import io.yunfei.github.network.GanKApiService;
import io.yunfei.github.network.RxUtils;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class HomePresenter extends RxPresenter<HomeView> {

  private GanKApiService mGanKApiService;

  public HomePresenter(GanKApiService mGanKApiService) {
    this.mGanKApiService = mGanKApiService;
  }

  public void getData() {
    Subscription subscribe = mGanKApiService.getDayData()
        .subscribeOn(RxUtils.io())
        .observeOn(RxUtils.main())
        .subscribe(new Action1<BaseResponse<DayEntity>>() {
          @Override public void call(BaseResponse<DayEntity> dayEntityBaseResponse) {

          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {

          }
        });
    addSuscription(subscribe);
  }
}
