package io.yunfei.github.network;

import android.content.Context;
import io.yunfei.github.dagger.AppModule;
import io.yunfei.github.entity.DayEntity;
import io.yunfei.github.test.utils.RxUnitTestTools;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import retrofit2.Retrofit;
import rx.functions.Action1;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */
public class GanKApiServiceTest {

  private GanKApiService mGanKApiService;

  @Before public void setUp() throws Exception {
    RxUnitTestTools.openRxTools();
    Context context = Mockito.mock(Context.class);

    AppModule appModule = new AppModule(context);
    OkHttpClient okHttpClient = appModule.provideOkHttpClient();
    Retrofit retrofit = appModule.provideRetrofit(okHttpClient);
    mGanKApiService = appModule.provideGanKApiService(retrofit);
  }

  @Test public void getDayData() throws Exception {
    mGanKApiService.getDayData()
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