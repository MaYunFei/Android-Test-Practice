package io.yunfei.github.dagger;

import dagger.Component;
import io.yunfei.github.view.home.HomeActivity;
import javax.inject.Singleton;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */
@Component(modules = { AppModule.class })@Singleton public interface AppComponent {

  void inject(HomeActivity homeActivity);
}
