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
  @BindView(R.id.btn_2) Button btn2;
  private DownloadBundle downloadBundle;
  private DownloadBundle m3u8Bundle;
  private DownloadBundle testM3u8Bundle;

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
    m3u8Bundle = getTestM3u8Bundle();

    downloadBundle = DownloadBundle.builder().title("title").unique_string("123").build();

    TaskEntity taskEntity = TaskEntity.builder()
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .url(
            "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk")
        .build();
    TaskEntity taskEntity1 = TaskEntity.builder()
        .url(
            "http://dl.m.cc.youku.com/android/phone/Youku_Phone_youkuweb.apk")
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
    downloadBundle.setTaskQueue(taskEntities);

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
    btn2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadTask task1 = DownloadManager.getInstance().getTask(m3u8Bundle.getUnique_string());
        if (task1 == null) {
          task1 = new DownloadTask(m3u8Bundle);
        }
        DownloadManager.getInstance().addTask(task1);
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

  public DownloadBundle getTestM3u8Bundle() {
    downloadBundle = DownloadBundle.builder().title("m3u8").unique_string("m3u8").build();

    TaskEntity taskEntity = TaskEntity.builder()
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .url(
            "https://mv.dongaocloud.com/2b4f/2b51/d42/278/d704d5c7c226a371f8b34926f14330f0/d704d5c7c226a371f8b34926f14330f0-000.ts")
        .build();
    TaskEntity taskEntity1 = TaskEntity.builder()
        .url(
            "https://mv.dongaocloud.com/2b4f/2b51/d42/278/d704d5c7c226a371f8b34926f14330f0/d704d5c7c226a371f8b34926f14330f0-001.ts")
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .build();
    TaskEntity taskEntity2 = TaskEntity.builder()
        .url(
            "https://mv.dongaocloud.com/2b4f/2b51/d42/278/d704d5c7c226a371f8b34926f14330f0/d704d5c7c226a371f8b34926f14330f0-002.ts")
        .taskStatus(TaskStatus.TASK_STATUS_INIT)
        .build();
    List<TaskEntity> taskEntities = new ArrayList<>();
    taskEntities.add(taskEntity);
    taskEntities.add(taskEntity1);
    taskEntities.add(taskEntity2);
    downloadBundle.setTaskQueue(taskEntities);
    return downloadBundle;
  }
}
