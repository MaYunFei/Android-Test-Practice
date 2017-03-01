package io.yunfei.github.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.yunfei.github.R;
import io.yunfei.github.base.BaseToolbarActivity;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public class HomeActivity extends BaseToolbarActivity {
  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Home");
  }
}
