package io.yunfei.github.m3u8.parser;

import io.yunfei.github.download.manager.TaskEntity;
import io.yunfei.github.download.parser.*;
import io.yunfei.github.network.RetrofitTestTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import okhttp3.OkHttpClient;
import org.junit.Test;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */
public class M3U8ParserTest {

  @Test public void parse() throws Exception {
    File file = new File(
        "/home/mayunfei/Documents/Code/GitHub/Android-Test-Practice/app/src/test/java/io/yunfei/github/online.m3u8");
    InputStream inputStream = new FileInputStream(file);
    //Playlist parse = m3U8Parser.parse();
    //
    //M3u8Parser m3U8Parser1 = new M3u8Parser(inputStream);
    //inputStream.close();
  }

  @Test public void M3u8Parse() throws Exception {

    OkHttpClient testOkHttpClient = RetrofitTestTool.getTestOkHttpClient();
    //m3U8Parser.setOkHttpClient(testOkHttpClient);
    //m3U8Parser.setUrl(
    //    "https://md.dongaocloud.com/2b4f/2b52/ff6/895/5ac9049db0f98f243b19bb6edba94110/video.m3u8");
    //m3U8Parser.parse();
    //List<TaskEntity> taskList = m3U8Parser.getTaskList();
    //for (TaskEntity task : taskList) {
    //  System.out.println(task);
    //}

    //PaseNetM3u8Task paseNetM3u8Task =  new PaseNetM3u8Task();
    //paseNetM3u8Task.setOkHttpClient(testOkHttpClient);
    //paseNetM3u8Task.setUrl("https://md.dongaocloud.com/2b4f/2b52/ff6/895/5ac9049db0f98f243b19bb6edba94110/video.m3u8");
    //paseNetM3u8Task.run();
  }
  @Test public void HtmlParse() throws Exception {

    OkHttpClient testOkHttpClient = RetrofitTestTool.getTestOkHttpClient();
    HtmlTaskParser htmlParser = new HtmlTaskParser("http://www.jianshu.com/p/850af4f09b61");
    htmlParser.setOkHttpClient(testOkHttpClient);
    htmlParser.setUrl(
        "http://www.jianshu.com/p/850af4f09b61");
    htmlParser.parse();
    List<TaskEntity> taskList = htmlParser.getTaskList();
    for (TaskEntity task : taskList) {
      System.out.println(task);
    }

  }


}