package io.yunfei.github.download.db;

import android.database.Cursor;

/**
 * Created by mayunfei on 17-3-9.
 */

public class DBHelper {
  public static int getInt(Cursor cursor, String columnName)
  {
    if (cursor != null)
    {
      int i = cursor.getColumnIndex(columnName);
      return cursor.getInt(i);
    }
    return 0;

  }

  public static long getLong(Cursor cursor, String columnName)
  {
    if (cursor != null)
    {
      int i = cursor.getColumnIndex(columnName);
      return cursor.getLong(i);
    }
    return 0;

  }

  public static String getString(Cursor cursor, String columnName)
  {
    if (cursor != null)
    {
      int i = cursor.getColumnIndex(columnName);
      return cursor.getString(i);
    }
    return "";
  }

}
