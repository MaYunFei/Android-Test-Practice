package io.yunfei.github.network;

import io.yunfei.github.App;
import io.yunfei.github.utils.AppUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class ParameInterceptor implements Interceptor {
  @Override public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    Request request;
    String method = originalRequest.method();
    //Headers headers = originalRequest.headers();
    Map<String, String> commonParams = getCommonParams();
    Headers headers =
        originalRequest.headers().newBuilder().add("User-Agent", "yunfei").build();
    //get 公共参数
    if (method.toUpperCase().equals("GET")) {

      HttpUrl.Builder builder = originalRequest.url().newBuilder();
      //添加公共参数
      for (Map.Entry<String, String> entry : commonParams.entrySet()) {
        builder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
      }
      HttpUrl modifiedUrl = builder.build();

      request = originalRequest.newBuilder().url(modifiedUrl).headers(headers).build();
      return chain.proceed(request);
    }

    //post 公共参数
    if (method.toUpperCase().equals("POST")) {
      Request.Builder requestBuilder = originalRequest.newBuilder();
      FormBody.Builder formBodyBuilder = new FormBody.Builder();
      for (Map.Entry<String, String> entry : commonParams.entrySet()) {
        formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
      }

      RequestBody formBody = formBodyBuilder.build();
      String postBodyString = bodyToString(originalRequest.body());
      postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
      requestBuilder.post(
          RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
              postBodyString));
      request = requestBuilder.headers(headers).build();
      return chain.proceed(request);
    }

    //request = originalRequest.newBuilder().url(modifiedUrl).build();
    return chain.proceed(originalRequest.newBuilder().build());
  }

  private Map<String, String> getCommonParams() {
    Map<String, String> params = new HashMap<>();
    params.put("version", AppUtils.getAppVersionCode(App.getInstance()) + "");
    params.put("app", App.getInstance().getPackageName());
    params.put("appName", "open-Account-app");
    params.put("timeStamp", System.currentTimeMillis() + "");
    return params;
  }

  private static String bodyToString(final RequestBody request) {
    try {
      final RequestBody copy = request;
      final Buffer buffer = new Buffer();
      if (copy != null) {
        copy.writeTo(buffer);
      } else {
        return "";
      }
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "did not work";
    }
  }
}
