package io.yunfei.github.network;

import io.yunfei.github.entity.DayEntity;
import io.yunfei.github.test.utils.RxUnitTestTools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.functions.Action1;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */
public class GanKApiServiceTest {

  private GanKApiService mGanKApiService;

  @Before public void setUp() throws Exception {
    RxUnitTestTools.setUpRxTools();
    mGanKApiService = RetrofitTestTool.getTestRetrofit().create(GanKApiService.class);
  }

  @After public void tearDown() throws Exception {


  }

  @Test public void getDayData() throws Exception {
    mGanKApiService.getDayData()
        .subscribeOn(RxUtils.io())
        .observeOn(RxUtils.main())
        .compose(ResponseUtils.<BaseResponse<DayEntity>, DayEntity>handleServerError())
        .subscribe(new Action1<DayEntity>() {
          @Override public void call(DayEntity dayEntity) {
            System.out.print("success   "+dayEntity.toString());
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            System.out.print("error " + throwable.getMessage());
          }
        });
  }
}