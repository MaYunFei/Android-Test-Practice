package io.yunfei.github.download.manager;

import lombok.Data;

/**
 * Created by mayunfei on 17-3-9.
 */

@Data public class TaskEntity {

  public static final String TASK_TABLE_NAME = "item_status";
  public static final String TASK_ID = "taskId";
  public static final String BUNDLE_ID = DownloadBundle.BUNDLE_ID;
  public static final String TOTAL_SEIZE = "totalSize";
  public static final String COMPLETED_SIZE = "completedSize";
  public static final String URL = "url";
  public static final String FILE_PATH = "filePath";
  public static final String FILE_NAME = "fileName";
  public static final String TASK_STATUS = "taskStatus";
  public static final String ARG0 = "arg0";


  public static final String CREATE_SQL = "CREATE TABLE if not exists "+
      TASK_TABLE_NAME
      + "("
      + TASK_ID + " INTEGER PRIMARY KEY,"
      + BUNDLE_ID + " INTEGER,"
      + TOTAL_SEIZE + " LONG,"
      + COMPLETED_SIZE + " LONG,"
      + URL + " TEXT,"
      + FILE_PATH +" TEXT,"
      + FILE_NAME +" TEXT,"
      + TASK_STATUS +" INTEGER,"
      + ARG0 +" TEXT"
      + ");";

  private int taskId;
  private int bundleId;
  private long totalSize;
  private long completedSize;
  private String url;
  private String filePath;
  private String fileName;
  private int taskStatus;
  private String arg0;
}
