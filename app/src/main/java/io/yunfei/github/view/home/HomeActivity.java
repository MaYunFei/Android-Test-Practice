package io.yunfei.github.view.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import io.yunfei.github.R;
import io.yunfei.github.base.BaseToolbarActivity;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.DownloadManager;
import io.yunfei.github.download.manager.DownloadTask;
import io.yunfei.github.download.manager.DownloadTaskListener;
import io.yunfei.github.download.manager.TaskEntity;
import io.yunfei.github.download.manager.TaskStatus;
import io.yunfei.github.entity.DayEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public class HomeActivity extends BaseToolbarActivity implements HomeView, DownloadTaskListener {

  private static final String TAG = "HomeActivity";

  private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
  @Inject HomePresenter mHomePresenter;
  @BindView(R.id.btn_status) Button btnStatus;
  private DownloadBundle downloadBundle;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Home");
    ComponentHolder.getAppComponent().inject(this);
    mHomePresenter.attachView(this);
    //mHomePresenter.getData();

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
          MY_PERMISSIONS_REQUEST_CALL_PHONE);
    }

    downloadBundle = DownloadBundle.builder().title("title").unique_string("123").build();

    TaskEntity taskEntity = TaskEntity.builder()
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .url(
            "https://dl.last.fm/static/1489125033/131211148/2b99f533b7ea77fc3e11c78bcd3bfc94bbeeac92ca492ffe936a79b2b068092e/Death+Grips+-+Get+Got.mp3")
        .build();
    TaskEntity taskEntity1 = TaskEntity.builder()
        .url(
            "https://dl.last.fm/static/1489125033/131627927/c167a409f8cf18e6b097c09d3c563141e66b397dd0075ce32ebf705f29a8c445/Death+Grips+-+I%27ve+Seen+Footage.mp3")
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .build();
    TaskEntity taskEntity2 = TaskEntity.builder()
        .url("http://img.wdjimg.com/mms/icon/v1/7/08/2b3858e31efdee8a7f28b06bdb83a087_512_512.png")
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .build();
    List<TaskEntity> taskEntities = new ArrayList<>();
    taskEntities.add(taskEntity);
    taskEntities.add(taskEntity1);
    taskEntities.add(taskEntity2);
    downloadBundle.setMTaskQueue(taskEntities);

    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadTask task =
            DownloadManager.getInstance().getTask(downloadBundle.getUnique_string());
        if (task == null) {
          task = new DownloadTask(downloadBundle);
        }
        task.setListener(HomeActivity.this);
        DownloadManager.getInstance().addTask(task);
      }
    });
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {

    if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      } else {
        //没有权限
      }
      return;
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mHomePresenter.detachView();
  }

  @Override public void showData(DayEntity dayEntity) {

  }

  @Override public void showError(Throwable throwable) {

  }

  @Override public void onQueue(DownloadTask downloadTask) {
    Log.e(TAG, "onQueue ");
    Log.e(TAG, downloadTask.toString());
  }

  @Override public void onConnecting(DownloadTask downloadTask) {
    Log.e(TAG, "onConnecting");
    Log.e(TAG, downloadTask.toString());
  }

  @Override public void onStart(final DownloadTask downloadTask) {
    Log.e(TAG, "onStart");
    Log.e(TAG, downloadTask.toString());
    btnStatus.setText("暂停");
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().pauseTask(downloadTask);
      }
    });
  }

  @Override public void onPause(final DownloadTask downloadTask) {
    Log.e(TAG, "onPause");
    Log.e(TAG, downloadTask.toString());
    btnStatus.setText("重启");
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().resumeTask(downloadTask);
      }
    });
  }

  @Override public void onCancel(final DownloadTask downloadTask) {
    Log.e(TAG, "onCancel");
    Log.e(TAG, downloadTask.toString());
    btnStatus.setText("开始");
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().addTask(downloadTask);
      }
    });
  }

  @Override public void onFinish(DownloadTask downloadTask) {
    Log.e(TAG, "onFinish");
    Log.e(TAG, downloadTask.toString());
    btnStatus.setText("完成");
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
  }

  @Override public void onError(final DownloadTask downloadTask, int code) {
    Log.e(TAG, "onError");
    Log.e(TAG, downloadTask.toString());
    btnStatus.setText("失败");
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().addTask(downloadTask);
      }
    });
  }
}
