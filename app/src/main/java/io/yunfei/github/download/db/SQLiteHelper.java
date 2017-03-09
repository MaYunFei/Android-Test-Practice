package io.yunfei.github.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.yunfei.github.download.manager.DownloadBundle;
import io.yunfei.github.download.manager.TaskEntity;

/**
 * Created by mayunfei on 17-3-9.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "download_manager.db";
  private static final int VERSION = 1;

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(DownloadBundle.CREATE_SQL);
    sqLiteDatabase.execSQL(TaskEntity.CREATE_SQL);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //数据库升级
  }
}
