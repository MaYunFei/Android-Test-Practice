package io.yunfei.github.m3u8.parser;

import io.yunfei.github.m3u8.data.ExtInfo;
import io.yunfei.github.m3u8.util.Constants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class ExtInfoParser {
  private final String DURATION_REGEXP = Constants.EXT_INF_TAG + ":(.*?) ";
  private final String TVG_ID_REGEXP = Constants.TVG_ID_TAG + "=\"(.*?)\"";
  private final String TVG_NAME_REGEXP = Constants.TVG_NAME_TAG + "=\"(.*?)\"";
  private final String TVG_LOGO_URL_REGEXP = Constants.TVG_LOGO_TAG + "=\"(.*?)\"";
  private final String GROUP_TITLE_REGEXP = Constants.GROUP_TITLE_TAG + "=\"(.*?)\"";
  private final String TITLE_REGEXP = "\",";
  private final Pattern durationPattern;
  private final Pattern tvgIdPattern;
  private final Pattern tvgNamePattern;
  private final Pattern tvgLogoUrlPattern;
  private final Pattern groupTitlePattern;
  private final Pattern titlePattern;

  public ExtInfoParser() {
    durationPattern = Pattern.compile(DURATION_REGEXP);
    tvgIdPattern = Pattern.compile(TVG_ID_REGEXP);
    tvgNamePattern = Pattern.compile(TVG_NAME_REGEXP);
    tvgLogoUrlPattern = Pattern.compile(TVG_LOGO_URL_REGEXP);
    groupTitlePattern = Pattern.compile(GROUP_TITLE_REGEXP);
    titlePattern = Pattern.compile(TITLE_REGEXP);
  }

  public ExtInfo parse(final String line) {
    final ExtInfo extInfo = new ExtInfo();
    extInfo.setDuration(getInsideString(durationPattern, line));
    extInfo.setTvgId(getInsideString(tvgIdPattern, line));
    extInfo.setTvgName(getInsideString(tvgNamePattern, line));
    extInfo.setTvgLogoUrl(getInsideString(tvgLogoUrlPattern, line));
    extInfo.setGroupTitle(getInsideString(groupTitlePattern, line));
    extInfo.setTitle(getNextToString(titlePattern, line));
    return extInfo;
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

  /**
   * Get the string next to the pattern reg exp
   *
   * @param pattern
   * @param line
   * @return
   */
  private String getNextToString(final Pattern pattern, final String line) {
    final Matcher matcher = pattern.matcher(line);
    String result = "";
    if (matcher.find()) {
      result = line.substring(matcher.end());
    }
    return result;
  }
}
