package io.yunfei.github.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

@Module public class AppModule {

  private final Context mContext;

  public AppModule(Context context) {
    this.mContext = context;
  }

  @Provides
  public Context provideContext() {
    return mContext;
  }

  @Provides public OkHttpClient provideOkHttpClient() {
    OkHttpClient okhttpClient =
        new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
    return okhttpClient;
  }

  @Provides public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().client(okhttpClient).baseUrl("https://api.github.com").build();
    return retrofit;
  }


  @Provides
  public SharedPreferences provideSharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
