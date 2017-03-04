package io.yunfei.github.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by yunfei on 2017/2/21.
 * email mayunfei6@gmail.com
 */

public class AppUtils {
  private AppUtils() {
  }

  /**
   * 获取App版本号
   *
   * @param context 上下文
   * @return App版本号
   */
  public static String getAppVersionName(Context context) {
    return getAppVersionName(context, context.getPackageName());
  }

  /**
   * 获取App版本号
   *
   * @param context 上下文
   * @param packageName 包名
   * @return App版本号
   */
  public static String getAppVersionName(Context context, String packageName) {
    if (TextUtils.isEmpty(packageName)) return null;
    try {
      PackageManager pm = context.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(packageName, 0);
      return pi == null ? null : pi.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取App版本码
   *
   * @param context 上下文
   * @return App版本码
   */
  public static int getAppVersionCode(Context context) {
    return getAppVersionCode(context, context.getPackageName());
  }

  /**
   * 获取App版本码
   *
   * @param context 上下文
   * @param packageName 包名
   * @return App版本码
   */
  public static int getAppVersionCode(Context context, String packageName) {
    if (TextUtils.isEmpty(packageName)) return -1;
    try {
      PackageManager pm = context.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(packageName, 0);
      return pi == null ? -1 : pi.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return -1;
    }
  }
}
