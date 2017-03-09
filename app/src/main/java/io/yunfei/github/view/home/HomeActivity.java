package io.yunfei.github.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.yunfei.github.R;
import io.yunfei.github.base.BaseToolbarActivity;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.download.db.DownloadDao;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.TaskEntity;
import io.yunfei.github.downloadmanager.download.TaskStatus;
import io.yunfei.github.entity.DayEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public class HomeActivity extends BaseToolbarActivity implements HomeView {

  @Inject HomePresenter mHomePresenter;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Home");
    ComponentHolder.getAppComponent().inject(this);
    mHomePresenter.attachView(this);
    mHomePresenter.getData();
    DownloadDao downloadDao = new DownloadDao(this);
    DownloadBundle downloadBundle = new DownloadBundle();
    downloadBundle.setUnique_string("123");
    downloadBundle.setTitle("title");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setFileName("filename");
    taskEntity.setFilePath("path");
    taskEntity.setTaskStatus(TaskStatus.TASK_STATUS_INIT);
    List<TaskEntity> taskEntities = new ArrayList<>();
    taskEntities.add(taskEntity);
    downloadBundle.setMTaskQueue(taskEntities);
    downloadDao.insertDownLoadBundle(downloadBundle);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mHomePresenter.detachView();
  }

  @Override public void showData(DayEntity dayEntity) {

  }

  @Override public void showError(Throwable throwable) {

  }
}
