package io.yunfei.github.m3u8.net;

import io.yunfei.github.download.utils.IOUtils;
import io.yunfei.github.m3u8.parser.M3U8Parser;
import io.yunfei.github.m3u8.scanner.M3U8ItemScanner;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mayunfei on 17-3-13.
 */

public class PaseNetM3u8Task implements Runnable{
  private OkHttpClient okHttpClient;
  private String url;

  @Override public void run() {
    InputStream inputStream = null;
    try {
      Request request = new Request.Builder().url(url).build();
      Response response = okHttpClient.newCall(request).execute();

      if (response.isSuccessful()){
        inputStream = response.body().byteStream();
        M3u8Parser m3u8Parser = new M3u8Parser(inputStream);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      IOUtils.close(inputStream);
    }
  }

  public void setOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
