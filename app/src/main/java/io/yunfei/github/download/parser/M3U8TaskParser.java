package io.yunfei.github.download.parser;

import io.yunfei.github.download.manager.TaskEntity;
import io.yunfei.github.download.utils.IOUtils;
import io.yunfei.github.m3u8.net.M3u8Parser;
import io.yunfei.github.m3u8.util.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mayunfei on 17-3-13.
 */

public class M3U8TaskParser implements TaskParser {

  private List<TaskEntity> taskEntities;
  private OkHttpClient okHttpClient;
  private String url;
  private Scanner scanner;
  private final String EXTINF_TAG_PREFIX = "#EXTINF";
  public static final String NEW_LINE_CHAR = "\\r?\\n";

  public M3U8TaskParser(String url) {
    this.url = url;
    this.taskEntities = new ArrayList<>();
  }

  @Override public void parse() throws Exception {
    InputStream inputStream = null;
    try {
      Request request = new Request.Builder().url(url).build();
      Response response = okHttpClient.newCall(request).execute();

      if (response.isSuccessful()) {
        inputStream = response.body().byteStream();
        scanner = new Scanner(inputStream).useDelimiter(EXTINF_TAG_PREFIX);
        if (scanner.hasNext()) {
          String info = scanner.next();
          System.out.println(info);
        }

        while (scanner.hasNext()) {
          String next = scanner.next();
          String[] item = next.split(NEW_LINE_CHAR);
          String videoUrl = item[1];
          if (!videoUrl.toLowerCase().contains("http://") || !videoUrl.toLowerCase()
              .contains("https://")) {
            videoUrl = url.substring(url.lastIndexOf("/") + 1) + videoUrl;
          }
          TaskEntity taskEntity = TaskEntity.builder().url(videoUrl).build();
          taskEntities.add(taskEntity);
        }
      }
    } finally {
      IOUtils.close(inputStream);
    }
  }

  @Override public List<TaskEntity> getTaskList() {
    return taskEntities;
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }

  public void setOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
