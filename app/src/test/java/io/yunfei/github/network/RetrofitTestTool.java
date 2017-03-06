package io.yunfei.github.network;

import io.yunfei.github.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 17-3-4.
 */

public class RetrofitTestTool {
  private RetrofitTestTool() {
  }

  public static OkHttpClient getTestOkHttpClient(){
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build();
  }

  public static Retrofit getTestRetrofit(){
    return new Retrofit.Builder()
        .baseUrl(BuildConfig.TEST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(getTestOkHttpClient())
        .build();
  }

}
