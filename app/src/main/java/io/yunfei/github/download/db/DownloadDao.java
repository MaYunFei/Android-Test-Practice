package io.yunfei.github.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import io.yunfei.github.BuildConfig;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.TaskEntity;

import static io.yunfei.github.download.manager.DownloadBundle.*;
import static io.yunfei.github.download.manager.TaskEntity.TASK_ID;
import static io.yunfei.github.download.manager.TaskEntity.TASK_TABLE_NAME;

/**
 * Created by mayunfei on 17-3-9.
 */

public class DownloadDao {
  private SQLiteHelper mHelper;
  private boolean isDebug = false;
  private static String TAG = "DATA_TEST";

  public DownloadDao(Context context) {
    mHelper = new SQLiteHelper(context);
    isDebug = BuildConfig.DEBUG;
  }

  //TODO 判断数据库中是否存在 Bundle
  public boolean isExistDownLoadBundle(String unique_string) {
    SQLiteDatabase db = mHelper.getReadableDatabase();
    StringBuilder sqlB = new StringBuilder();
    sqlB.append("SELECT * ");
    sqlB.append("FROM " + DownloadBundle.BUNDLE_TABLE_NAME + " ");
    sqlB.append("WHERE ");
    sqlB.append("(");
    sqlB.append(DownloadBundle.UNIQUE_STRING).append(" = ").append(unique_string).append(" ");
    Cursor cursor = db.rawQuery(sqlB.toString(), null);
    if (cursor.getCount() > 0) {
      cursor.close();
      return true;
    } else {
      cursor.close();
      db.close();
      return false;
    }
  }

  /**
   *  只更新bundle
   * @param downloadBundle
   * @return
   */
  public boolean updateDownLoadBundle(DownloadBundle downloadBundle){
    SQLiteDatabase db = mHelper.getWritableDatabase();
    return insertDownloadBundle(db,downloadBundle);
  }


  /**
   * 插入下载任务包
   */
  public boolean insertDownLoadBundle(DownloadBundle downloadBundle) {
    //不符合条件的数据
    if (downloadBundle == null
        || downloadBundle.getMTaskQueue() == null
        || downloadBundle.getMTaskQueue().size() < 0) {
      return false;
    }
    SQLiteDatabase db = mHelper.getWritableDatabase();
    /**
     * 开启事务
     */
    db.beginTransaction();
    int maxId = getMaxID(db, BUNDLE_TABLE_NAME, BUNDLE_ID);
    try {
      downloadBundle.setBundleId(maxId);
      insertDownloadBundle(db, downloadBundle);
      int maxTaskId = getMaxID(db, TASK_TABLE_NAME, TASK_ID);
      for (TaskEntity taskEntity : downloadBundle.getMTaskQueue()) {
        taskEntity.setTaskId(maxTaskId);
        taskEntity.setBundleId(maxId);
        insertTaskEntity(db, taskEntity);
        maxTaskId++;
      }

      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
      db.close();
    }

    return true;
  }

  private int getMaxID(SQLiteDatabase db, String tableName, String idName) {
    StringBuilder sqlB = new StringBuilder();
    sqlB.append("SELECT max(").append(idName).append(") AS maxId ");
    sqlB.append("FROM ").append(tableName);
    if (isDebug) {
      Log.e(TAG, "sql = " + sqlB.toString());
    }
    int maxId = 1;
    Cursor cursor = db.rawQuery(sqlB.toString(), null);
    if (cursor.moveToFirst()) {
      maxId = DBHelper.getInt(cursor, "maxId");

      if (maxId > 0) {
        return maxId + 1;
      }

      return 1;
    }
    return 1;
  }

  private boolean insertDownloadBundle(SQLiteDatabase db, DownloadBundle downloadBundle) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(BUNDLE_ID, downloadBundle.getBundleId());
    contentValues.put(TITLE, downloadBundle.getTitle());
    contentValues.put(UNIQUE_STRING, downloadBundle.getUnique_string());
    contentValues.put(ARG0, downloadBundle.getArg0());
    contentValues.put(ARG1, downloadBundle.getArg1());
    contentValues.put(STATUS, downloadBundle.getStatus());
    contentValues.put(FILE_PATH, downloadBundle.getFilePath());
    long insert = db.insert(DownloadBundle.BUNDLE_TABLE_NAME, null, contentValues);
    Log.e(TAG, "insert bundle " + insert);

    return true;
  }

  private boolean insertTaskEntity(SQLiteDatabase db, TaskEntity taskEntity) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TaskEntity.TASK_ID, taskEntity.getTaskId());
    contentValues.put(TaskEntity.BUNDLE_ID, taskEntity.getBundleId());
    contentValues.put(TaskEntity.TOTAL_SEIZE, taskEntity.getTotalSize());
    contentValues.put(TaskEntity.COMPLETED_SIZE, taskEntity.getCompletedSize());
    contentValues.put(TaskEntity.URL, taskEntity.getUrl());
    contentValues.put(TaskEntity.FILE_PATH, taskEntity.getFilePath());
    contentValues.put(TaskEntity.FILE_NAME, taskEntity.getFileName());
    contentValues.put(TaskEntity.TASK_STATUS, taskEntity.getTaskStatus());
    contentValues.put(TaskEntity.ARG0, taskEntity.getArg0());
    long insert = db.insert(TaskEntity.TASK_TABLE_NAME, null, contentValues);
    Log.e(TAG, "insert task " + insert);
    return true;
  }
}
