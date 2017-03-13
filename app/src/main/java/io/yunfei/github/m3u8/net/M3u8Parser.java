package io.yunfei.github.m3u8.net;

import io.yunfei.github.m3u8.util.Constants;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mayunfei on 17-3-13.
 */

public class M3u8Parser {
  private InputStream inputStream;
  private Scanner scanner;

  public M3u8Parser(InputStream inputStream) {
    this.inputStream = inputStream;
    scanner = new Scanner(inputStream).useDelimiter("#EXTINF");
    if (scanner.hasNext()){
      String info = scanner.next();
      //Pattern keyPattern = Pattern.compile("EXT-X-KEY:(.*?)URI=\"(.*?)\"");
      //String key = getInsideString(keyPattern, info);
      //System.out.println(key);
      System.out.println(info);
    }

    while (scanner.hasNext()){
      System.out.println(scanner.next());
    }
  }


  /**
   * Get the string wrapped inside the pattern reg exp
   *
   * @param pattern
   * @param line
   * @return
   */
  private String getInsideString(final Pattern pattern, final String line) {
    final Matcher matcher = pattern.matcher(line);
    String result = "";
    if (matcher.find()) {
      result = matcher.group(1);
    }
    return result;
  }

}
