package io.yunfei.github.download.parser;

import io.yunfei.github.download.manager.TaskEntity;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;

/**
 * Created by mayunfei on 17-3-13.
 */

public interface TaskParser {
  //获得任务列表
  List<TaskEntity> getTaskList();

  //解析任务
  void parse() throws Throwable;

  void setOkHttpClient(OkHttpClient okHttpClient);

}
