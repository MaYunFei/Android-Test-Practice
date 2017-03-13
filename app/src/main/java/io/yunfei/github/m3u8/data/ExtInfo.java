package io.yunfei.github.m3u8.data;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class ExtInfo {
  private String duration;
  private String tvgId;
  private String tvgName;
  private String tvgLogoUrl;
  private String groupTitle;
  private String title;

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getTvgId() {
    return tvgId;
  }

  public void setTvgId(String tvgId) {
    this.tvgId = tvgId;
  }

  public String getTvgName() {
    return tvgName;
  }

  public void setTvgName(String tvgName) {
    this.tvgName = tvgName;
  }

  public String getTvgLogoUrl() {
    return tvgLogoUrl;
  }

  public void setTvgLogoUrl(String tvgLogoUrl) {
    this.tvgLogoUrl = tvgLogoUrl;
  }

  public String getGroupTitle() {
    return groupTitle;
  }

  public void setGroupTitle(String groupTitle) {
    this.groupTitle = groupTitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override public String toString() {
    return "duration = "+duration + "\n"
        +"tvgId = "+tvgId + "\n"
        +"tvgName = "+tvgName + "\n"
        +"tvgLogoUrl = "+tvgLogoUrl + "\n"
        +"groupTitle = "+groupTitle + "\n"
        +"title = "+title + "\n"
        ;
  }
}
