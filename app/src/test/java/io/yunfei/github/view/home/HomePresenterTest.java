package io.yunfei.github.view.home;

import io.yunfei.github.entity.DayEntity;
import io.yunfei.github.network.GanKApiService;
import io.yunfei.github.network.RetrofitTestTool;
import io.yunfei.github.test.utils.RxUnitTestTools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */
public class HomePresenterTest {
  private HomePresenter mHomePresenter;
  private HomeView mHomeView;

  @Before public void setUp() throws Exception {
    RxUnitTestTools.setUpRxTools();
    mHomePresenter =
        new HomePresenter(RetrofitTestTool.getTestRetrofit().create(GanKApiService.class));
    mHomeView = mock(HomeView.class);
    mHomePresenter.attachView(mHomeView);
  }

  @After public void tearDown() throws Exception {
    mHomePresenter.detachView();

  }

  @Test public void getData() throws Exception {
    mHomePresenter.getData();
    verify(mHomeView, times(1)).showData(any(DayEntity.class));
  }
}