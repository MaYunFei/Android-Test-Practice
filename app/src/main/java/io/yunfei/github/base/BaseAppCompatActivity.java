package io.yunfei.github.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    initToolbar(savedInstanceState);
    initViews(savedInstanceState);
    initListeners();
  }

  /**
   * 设置toolbar
   */
  protected void initToolbar(Bundle savedInstanceState) {

  }

  /**
   * 初始化view
   */
  protected void initViews(Bundle savedInstanceState) {

  }

  /**
   * 设置监听
   */
  protected void initListeners() {

  }

  /**
   * 主布局
   */
  protected abstract int getLayoutId();
}
