package io.yunfei.github.download.parser;

import io.yunfei.github.download.manager.TaskEntity;
import io.yunfei.github.download.utils.IOUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by mayunfei on 17-3-13.
 */

public class HtmlTaskParser implements TaskParser {

  private List<TaskEntity> taskEntities;
  private OkHttpClient okHttpClient;
  private String url;

  public HtmlTaskParser(String url) {
    this.url = url;
    taskEntities = new ArrayList<>();
  }

  @Override public List<TaskEntity> getTaskList() {
    return taskEntities;
  }

  @Override public void parse() throws Exception {
    InputStream inputStream = null;
    List<String> list = new ArrayList<>();
    try {
      Request request = new Request.Builder().url(url).build();
      Response response = okHttpClient.newCall(request).execute();

      if (response.isSuccessful()) {
        String htmlContent = response.body().string();
        int index = url.lastIndexOf("/");
        String rootUrl = url.substring(0, index + 1);

        Document doc = Jsoup.parse(htmlContent);
        Elements csss = doc.select("link");
        Elements jss = doc.select("script");
        Elements imgs = doc.select("img");
        for (Element element : csss) {
          String result = element.attr("href");
          String path = toAbsolutePath(rootUrl, result);

          if (!list.contains(path)) list.add(toAbsolutePath(rootUrl, result));
          // 替换文件为相对路径
          element.attr("href", result.substring(result.lastIndexOf("/") + 1));
        }
        for (Element element : jss) {
          String result = element.attr("src");
          String path = toAbsolutePath(rootUrl, result);
          if (!list.contains(path)) list.add(path);
          // 替换文件为相对路径
          element.attr("src", result.substring(result.lastIndexOf("/") + 1));
        }
        for (Element element : imgs) {
          String result = element.attr("src");
          String path = toAbsolutePath(rootUrl, result);
          if (!list.contains(path)) list.add(path);
          // 替换文件为相对路径
          element.attr("src", result.substring(result.lastIndexOf("/") + 1));
        }
        for (String path : list) {
          TaskEntity taskEntity = TaskEntity.builder().url(path).build();
          taskEntities.add(taskEntity);
        }
        // 得到新的html文本
        String newHtmlContent = doc.html();

        //TODO 存储

      }
    } finally {
      IOUtils.close(inputStream);
    }
  }

  private String toAbsolutePath(String rootUrl, String url) {
    String result = "";
    if (url.startsWith("http://") || url.startsWith("https://")) {
      result = url;
    } else {
      result = rootUrl + url;
    }
    return result;
  }

  @Override
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
