package io.yunfei.github.download.manager;

import io.yunfei.github.download.parser.TaskParser;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by mayunfei on 17-3-9.
 */

@Data @Builder public class DownloadBundle {

  public static final String BUNDLE_TABLE_NAME = "bundles";
  public static final String BUNDLE_ID = "bundleId";
  public static final String TITLE = "title";
  public static final String UNIQUE_STRING = "unique_string";
  public static final String ARG0 = "arg0";
  public static final String ARG1 = "arg1";
  public static final String STATUS = "status";
  public static final String FILE_PATH = "filePath";
  public static final String TOTAL_SEIZE = "totalSize";
  public static final String COMPLETED_SIZE = "completedSize";

  /**
   * 不能是自增的id 因为会导致导入导出 出问题，为了能够将数据导出
   */
  public static final String CREATE_SQL = "CREATE TABLE if not exists "+
      BUNDLE_TABLE_NAME
      + "("
      + BUNDLE_ID + " INTEGER PRIMARY KEY,"
      + TITLE + " TEXT,"
      + UNIQUE_STRING + " TEXT,"
      + FILE_PATH +" TEXT,"
      + TOTAL_SEIZE + " LONG,"
      + COMPLETED_SIZE + " LONG,"
      + STATUS + " INTEGER,"
      + ARG0 +" TEXT,"
      + ARG1 +" TEXT"
      + ");";


  private int bundleId;
  private String title;
  private String unique_string;
  private String arg0;
  private String arg1;
  private int status;
  private String filePath;
  private long totalSize;
  private long completedSize;
  private List<TaskEntity> TaskQueue;
  private List<TaskParser> TaskParsers;


}
