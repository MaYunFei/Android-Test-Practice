package io.yunfei.github.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import io.yunfei.github.network.GanKApiService;
import io.yunfei.github.view.home.HomePresenter;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

@Module public class AppModule {

  private final Context mContext;

  public AppModule(Context context) {
    this.mContext = context;
  }

  @Singleton @Provides public Context provideContext() {
    return mContext;
  }

  @Singleton @Provides public OkHttpClient provideOkHttpClient() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okhttpClient =
        new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(30, TimeUnit.SECONDS).build();
    return okhttpClient;
  }

  @Singleton @Provides public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl("http://gank.io/api/").build();
    return retrofit;
  }

  @Singleton @Provides public SharedPreferences provideSharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Singleton @Provides public GanKApiService provideGanKApiService(Retrofit retrofit) {
    return retrofit.create(GanKApiService.class);
  }

  @Singleton @Provides public HomePresenter provideHomePresenter(GanKApiService apiService) {
    return new HomePresenter(apiService);
  }
}
