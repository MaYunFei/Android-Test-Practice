package io.yunfei.github.m3u8.scanner;

import io.yunfei.github.m3u8.util.Constants;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class M3U8ItemScanner {
  private final String EXTINF_TAG_PREFIX = Constants.EXT_INF_TAG;
  private final Scanner scanner;

  public M3U8ItemScanner(final InputStream inputStream, final Encoding encoding) {
        scanner = new Scanner(inputStream, encoding.getValue()).useLocale(Locale.US).useDelimiter(EXTINF_TAG_PREFIX);
    }

  public boolean hasNext() {
    return scanner.hasNext();
  }

  public String next() throws ParseException {
    final String line = scanner.next();
    return EXTINF_TAG_PREFIX + line;
  }

  public enum Encoding {
    UTF_8("utf-8");

    private final String value;

    Encoding(final String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
