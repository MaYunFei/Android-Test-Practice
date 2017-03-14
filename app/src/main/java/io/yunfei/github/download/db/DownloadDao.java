package io.yunfei.github.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import io.yunfei.github.BuildConfig;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.TaskEntity;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

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

  public Observable<Boolean> isExistDownLoadBundleIo(final String unique_string) {
    return Observable.just(isExistDownLoadBundle(unique_string)).subscribeOn(Schedulers.io());
  }

  //TODO 判断数据库中是否存在 Bundle
  public boolean isExistDownLoadBundle(String unique_string) {
    SQLiteDatabase db = mHelper.getReadableDatabase();
    String sqlB = "SELECT * " +
        "FROM " + DownloadBundle.BUNDLE_TABLE_NAME + " " +
        "WHERE " +
        "(" +
        DownloadBundle.UNIQUE_STRING + " = " + unique_string + " ";
    Cursor cursor = db.rawQuery(sqlB, null);
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
   * 插入下载任务包
   */
  public boolean insertDownLoadBundle(DownloadBundle downloadBundle) {
    //不符合条件的数据
    if (downloadBundle == null
        || downloadBundle.getTaskQueue() == null
        || downloadBundle.getTaskQueue().size() < 0) {
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
      for (TaskEntity taskEntity : downloadBundle.getTaskQueue()) {
        taskEntity.setTaskId(maxTaskId);
        taskEntity.setBundleId(maxId);
        insertTaskEntity(db, taskEntity);
        maxTaskId++;
      }

      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
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

      if (maxId >= 0) {
        return maxId + 1;
      }
    }
    cursor.close();
    return maxId;
  }

  private boolean insertDownloadBundle(SQLiteDatabase db, DownloadBundle downloadBundle) {
    long insert =
        db.insert(DownloadBundle.BUNDLE_TABLE_NAME, null, getDownloadBundleValues(downloadBundle));
    if (BuildConfig.DEBUG) {
      Log.i(TAG, "insert " + downloadBundle.toString());
    }
    return insert > 0;
  }

  /**
   * 只更新bundle
   */
  public boolean updateDownLoadBundle(DownloadBundle downloadBundle) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    int update =
        db.update(DownloadBundle.BUNDLE_TABLE_NAME, getDownloadBundleValues(downloadBundle),
            DownloadBundle.BUNDLE_ID + " = ?", new String[] { downloadBundle.getBundleId() + "" });

    if (BuildConfig.DEBUG) {
      Log.i(TAG, "update " + downloadBundle.toString());
    }

    return update > 0;
  }

  private ContentValues getDownloadBundleValues(DownloadBundle downloadBundle) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(BUNDLE_ID, downloadBundle.getBundleId());
    contentValues.put(TITLE, downloadBundle.getTitle());
    contentValues.put(UNIQUE_STRING, downloadBundle.getUnique_string());
    contentValues.put(ARG0, downloadBundle.getArg0());
    contentValues.put(ARG1, downloadBundle.getArg1());
    contentValues.put(STATUS, downloadBundle.getStatus());
    contentValues.put(COMPLETED_SIZE, downloadBundle.getCompletedSize());
    contentValues.put(TOTAL_SEIZE, downloadBundle.getTotalSize());
    contentValues.put(FILE_PATH, downloadBundle.getFilePath());
    return contentValues;
  }

  private ContentValues getTaskEntityValues(TaskEntity taskEntity) {
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
    return contentValues;
  }

  private boolean insertTaskEntity(SQLiteDatabase db, TaskEntity taskEntity) {
    long insert = db.insert(TaskEntity.TASK_TABLE_NAME, null, getTaskEntityValues(taskEntity));
    if (BuildConfig.DEBUG) {
      Log.i(TAG, "insert " + taskEntity.toString());
    }
    return insert > 0;
  }

  public boolean updateTaskEntity(TaskEntity mTaskEntity) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    int update = db.update(TaskEntity.TASK_TABLE_NAME, getTaskEntityValues(mTaskEntity),
        TaskEntity.TASK_ID + " = ?", new String[] { mTaskEntity.getTaskId() + "" });
    if (BuildConfig.DEBUG) {
      Log.i(TAG, "update  " + mTaskEntity.toString());
    }
    return update > 0;
  }

  public DownloadBundle queryDownLoadBundle(String unique) {

    SQLiteDatabase db = mHelper.getReadableDatabase();
    String sqlB = "SELECT * FROM " +
        DownloadBundle.BUNDLE_TABLE_NAME +
        " WHERE " +
        DownloadBundle.UNIQUE_STRING +
        " = ? ";
    Cursor cursor = db.rawQuery(sqlB, new String[] { unique });
    try {
      if (cursor.moveToNext()) {
        int bundleId = DBHelper.getInt(cursor, DownloadBundle.BUNDLE_ID);
        String title = DBHelper.getString(cursor, DownloadBundle.TITLE);
        String unique_string = DBHelper.getString(cursor, DownloadBundle.UNIQUE_STRING);
        String arg0 = DBHelper.getString(cursor, DownloadBundle.ARG0);
        String arg1 = DBHelper.getString(cursor, DownloadBundle.ARG1);
        int status = DBHelper.getInt(cursor, DownloadBundle.STATUS);
        String filePath = DBHelper.getString(cursor, DownloadBundle.FILE_PATH);
        long totalSize = DBHelper.getLong(cursor, DownloadBundle.TOTAL_SEIZE);
        long completedSize = DBHelper.getLong(cursor, DownloadBundle.COMPLETED_SIZE);

        DownloadBundle bundle = DownloadBundle.builder()
            .bundleId(bundleId)
            .title(title)
            .unique_string(unique_string)
            .arg0(arg0)
            .arg1(arg1)
            .status(status)
            .filePath(filePath)
            .totalSize(totalSize)
            .completedSize(completedSize)
            .build();

        bundle.setTaskQueue(getTaskEntityListByBundleId(db, bundleId));
        return bundle;
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }

    return null;
  }

  private List<TaskEntity> getTaskEntityListByBundleId(SQLiteDatabase db, int bundleId) {
    List<TaskEntity> entityList = new ArrayList<>();
    String sqlB = "SELECT * FROM " +
        TaskEntity.TASK_TABLE_NAME +
        " WHERE " +
        TaskEntity.BUNDLE_ID +
        " = ? ";

    Cursor cursor = db.rawQuery(sqlB, new String[] { bundleId + "" });
    try {
      while (cursor.moveToNext()) {
        int taskId = DBHelper.getInt(cursor, TaskEntity.TASK_ID);
        //int bundleId = DBHelper.getInt(cursor,TaskEntity.BUNDLE_ID);
        long totalSize = DBHelper.getLong(cursor, TaskEntity.TOTAL_SEIZE);
        long completedSize = DBHelper.getLong(cursor, TaskEntity.COMPLETED_SIZE);
        String url = DBHelper.getString(cursor, TaskEntity.URL);
        String filePath = DBHelper.getString(cursor, TaskEntity.FILE_PATH);
        String fileName = DBHelper.getString(cursor, TaskEntity.FILE_NAME);
        int taskStatus = DBHelper.getInt(cursor, TaskEntity.TASK_STATUS);
        String arg0 = DBHelper.getString(cursor, TaskEntity.ARG0);
        TaskEntity build = TaskEntity.builder()
            .taskId(taskId)
            .bundleId(bundleId)
            .totalSize(totalSize)
            .completedSize(completedSize)
            .url(url)
            .filePath(filePath)
            .fileName(fileName)
            .taskStatus(taskStatus)
            .arg0(arg0)
            .build();

        entityList.add(build);
      }
    } finally {
      cursor.close();
    }

    return entityList;
  }

  public void delete(DownloadBundle downloadBundle) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    db.delete(TaskEntity.TASK_TABLE_NAME, TaskEntity.BUNDLE_ID + "=?",
        new String[] { downloadBundle.getBundleId() + "" });
    db.delete(DownloadBundle.BUNDLE_TABLE_NAME, DownloadBundle.UNIQUE_STRING + " = ?",
        new String[] { downloadBundle.getUnique_string() });
    db.close();
  }


}
