package io.yunfei.github.m3u8.data;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class Track implements Comparable<Track> {
  private ExtInfo extInfo;
  private String url;

  @Override public int compareTo(Track track) {
    return extInfo.getTitle().compareTo(track.getExtInfo().getTitle());
  }

  public ExtInfo getExtInfo() {
    return extInfo;
  }

  public void setExtInfo(ExtInfo extInfo) {
    this.extInfo = extInfo;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
