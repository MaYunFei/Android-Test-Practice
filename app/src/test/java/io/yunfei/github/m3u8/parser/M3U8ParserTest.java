package io.yunfei.github.m3u8.parser;

import io.yunfei.github.m3u8.data.Playlist;
import io.yunfei.github.m3u8.scanner.M3U8ItemScanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */
public class M3U8ParserTest {


  @Test public void parse() throws Exception {
    File file = new File("/Users/yunfei/Documents/Code/GitHub/AndroidTest/app/src/test/java/io/yunfei/github/online.m3u8");
    InputStream inputStream = new FileInputStream(file);
    M3U8Parser m3U8Parser = new M3U8Parser(inputStream, M3U8ItemScanner.Encoding.UTF_8);
    Playlist parse = m3U8Parser.parse();
    inputStream.close();
  }
}