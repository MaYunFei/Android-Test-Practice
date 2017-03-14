package io.yunfei.github.view.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import io.yunfei.github.R;
import io.yunfei.github.base.BaseToolbarActivity;
import io.yunfei.github.dagger.ComponentHolder;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.DownloadManager;
import io.yunfei.github.download.manager.DownloadTask;
import io.yunfei.github.download.manager.DownloadTaskListener;
import io.yunfei.github.download.manager.TaskStatus;
import io.yunfei.github.download.parser.M3U8TaskParser;
import io.yunfei.github.download.parser.TaskParser;
import io.yunfei.github.entity.DayEntity;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by yunfei on 2017/3/1.
 * email mayunfei6@gmail.com
 */

public class HomeActivity extends BaseToolbarActivity implements HomeView {

  private static final String TAG = "HomeActivity";

  private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
  @Inject HomePresenter mHomePresenter;
  @BindView(R.id.btn_status) Button btnStatus;
  @BindView(R.id.btn_2) Button btn2;
  @BindView(R.id.btn_3) Button btn3;
  @BindView(R.id.btn_4) Button btn4;
  @BindView(R.id.btn_start_all) Button btnStartAll;
  @BindView(R.id.btn_pause_all) Button btnPauseAll;

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
    btnStartAll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().startAll();
      }
    });
    btnPauseAll.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadManager.getInstance().pauseAll();
      }
    });
    btnStatus.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        DownloadBundle test1 = getTest1();

        DownloadTask task = DownloadManager.getInstance().getTask(test1.getUnique_string());
        if (task == null) {
          task = new DownloadTask(test1);
        }

        DownloadManager.getInstance().addTask(task);

        task.setListener(new DownloadTaskListener() {
          @Override public void onQueue(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onConnecting(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onStart(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onPause(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onCancel(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onFinish(DownloadTask downloadTask) {
            btnStatusChanger(btnStatus, downloadTask);
          }

          @Override public void onError(DownloadTask downloadTask, int code) {
            btnStatusChanger(btnStatus, downloadTask);
          }
        });
      }
    });
    btn2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadBundle test2 = getTest2();
        DownloadTask task1 = DownloadManager.getInstance().getTask(test2.getUnique_string());
        if (task1 == null) {
          task1 = new DownloadTask(test2);
        }
        DownloadManager.getInstance().addTask(task1);

        task1.setListener(new DownloadTaskListener() {
          @Override public void onQueue(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onConnecting(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onStart(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onPause(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onCancel(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onFinish(DownloadTask downloadTask) {
            btnStatusChanger(btn2, downloadTask);
          }

          @Override public void onError(DownloadTask downloadTask, int code) {
            btnStatusChanger(btn2, downloadTask);
          }
        });
      }
    });

    btn3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadBundle test2 = getTest3();
        DownloadTask task1 = DownloadManager.getInstance().getTask(test2.getUnique_string());
        if (task1 == null) {
          task1 = new DownloadTask(test2);
        }
        DownloadManager.getInstance().addTask(task1);

        task1.setListener(new DownloadTaskListener() {
          @Override public void onQueue(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onConnecting(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onStart(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onPause(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onCancel(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onFinish(DownloadTask downloadTask) {
            btnStatusChanger(btn3, downloadTask);
          }

          @Override public void onError(DownloadTask downloadTask, int code) {
            btnStatusChanger(btn3, downloadTask);
          }
        });
      }
    });

    btn4.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DownloadBundle test2 = getTest4();
        DownloadTask task1 = DownloadManager.getInstance().getTask(test2.getUnique_string());
        if (task1 == null) {
          task1 = new DownloadTask(test2);
        }
        DownloadManager.getInstance().addTask(task1);

        task1.setListener(new DownloadTaskListener() {
          @Override public void onQueue(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onConnecting(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onStart(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onPause(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onCancel(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onFinish(DownloadTask downloadTask) {
            btnStatusChanger(btn4, downloadTask);
          }

          @Override public void onError(DownloadTask downloadTask, int code) {
            btnStatusChanger(btn4, downloadTask);
          }
        });
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

  //public DownloadBundle getTestM3u8Bundle() {
  //  ArrayList<TaskParser> taskParsers = new ArrayList<>();
  //  taskParsers.add(new M3U8TaskParser(
  //      "https://md.dongaocloud.com/2b4f/2b52/5b3/81e/61e08244fcd53892b90031ee873de2b2/video.m3u8"));
  //  downloadBundle = DownloadBundle.builder()
  //      .title("m3u8")
  //      .unique_string("m3u8")
  //      .TaskParsers(taskParsers)
  //      .build();
  //
  //  return downloadBundle;
  //}

  public DownloadBundle getTest1() {

    ArrayList<TaskParser> taskParsers = new ArrayList<>();
    taskParsers.add(new M3U8TaskParser(
        "https://md.dongaocloud.com/2b4f/2b52/5b3/81e/61e08244fcd53892b90031ee873de2b2/video.m3u8"));
    return DownloadBundle.builder()
        .title("test1")
        .unique_string("test1")
        .TaskParsers(taskParsers)
        .build();
  }

  public DownloadBundle getTest2() {

    ArrayList<TaskParser> taskParsers = new ArrayList<>();
    taskParsers.add(new M3U8TaskParser(
        "https://md.dongaocloud.com/2b4f/2b52/5b3/81e/5e624b842fe6eb2ff39d07c966c84055/video.m3u8"));
    return DownloadBundle.builder()
        .title("test2")
        .unique_string("test2")
        .TaskParsers(taskParsers)
        .build();
  }

  public DownloadBundle getTest3() {

    ArrayList<TaskParser> taskParsers = new ArrayList<>();
    taskParsers.add(new M3U8TaskParser(
        "https://md.dongaocloud.com/2b4f/2b52/5b3/81e/014423b6e2d448dca612a69ba9854ddc/video.m3u8"));
    return DownloadBundle.builder()
        .title("test3")
        .unique_string("test3")
        .TaskParsers(taskParsers)
        .build();
  }

  public DownloadBundle getTest4() {

    ArrayList<TaskParser> taskParsers = new ArrayList<>();
    taskParsers.add(new M3U8TaskParser(
        "https://md.dongaocloud.com/2b4f/2b52/5b3/81e/b2eedd06137ec7c0da664c3e32ff7b3c/video.m3u8"));
    return DownloadBundle.builder()
        .title("test4")
        .unique_string("test4")
        .TaskParsers(taskParsers)
        .build();
  }

  private void btnStatusChanger(final Button btn, final DownloadTask downloadTask) {
    switch (downloadTask.getDownloadBundle().getStatus()) {
      case TaskStatus.TASK_STATUS_CONNECTING:
      case TaskStatus.TASK_STATUS_DOWNLOADING:
        btn.setText("正在下载");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            DownloadManager.getInstance().pauseTask(downloadTask);
          }
        });
        //下载中的状态
        break;
      case TaskStatus.TASK_STATUS_INIT:
      case TaskStatus.TASK_STATUS_QUEUE:
        //初始化等待状态
        btn.setText("等待队列");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            DownloadManager.getInstance().pauseTask(downloadTask);
          }
        });
        break;

      case TaskStatus.TASK_STATUS_CANCEL:
        //取消状态
        btn.setText("取消");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            //一般就是删除了 不需要 再处理
            downloadTask.getDownloadBundle().getTaskQueue().clear();

            DownloadManager.getInstance().addTask(downloadTask);
          }
        });
        break;
      case TaskStatus.TASK_STATUS_PAUSE:
        //暂停
        btn.setText("暂停");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            DownloadManager.getInstance().resumeTask(downloadTask);
          }
        });
        break;
      case TaskStatus.TASK_STATUS_REQUEST_ERROR:
      case TaskStatus.TASK_STATUS_STORAGE_ERROR:
        //错误
        btn.setText("错误");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            //DownloadManager.getInstance()
            DownloadManager.getInstance().cancelTask(downloadTask);
          }
        });
        break;
      case TaskStatus.TASK_STATUS_FINISH:
        //完成
        btn.setText("完成");
        btn.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {

          }
        });
        break;
    }
  }
}
