package io.yunfei.github.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import io.yunfei.github.R;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public abstract class BaseToolbarActivity extends BaseAppCompatActivity {
  @BindView(R.id.toolbar) protected Toolbar mToolbar;
  @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBarLayout;
  private ActionBarHelper mActionBarHelper;

  @Override protected void initToolbar(Bundle savedInstanceState) {
    super.initToolbar(savedInstanceState);
    if (mToolbar == null || mAppBarLayout == null) {
      return;
    }
    setSupportActionBar(mToolbar);
    mActionBarHelper = new ActionBarHelper();
  }



  /**
   * ActionBar 管理类
   */
  public class ActionBarHelper {
    ActionBar mActionBar;

    public ActionBarHelper() {
      this.mActionBar = getSupportActionBar();
    }

    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
      if (this.mActionBar == null) return;
      this.mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    public void setDisplayShowTitleEnabled(boolean showTitleEnabled) {
      if (this.mActionBar == null) return;
      this.mActionBar.setDisplayShowTitleEnabled(showTitleEnabled);
    }
  }
}
