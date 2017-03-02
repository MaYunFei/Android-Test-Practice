package io.yunfei.github.entity;

import java.util.List;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class DayEntity {

  private List<DayItemEntity> Android;
  private List<DayItemEntity> iOS;
  private List<DayItemEntity> 休息视频;
  private List<DayItemEntity> 拓展资源;
  private List<DayItemEntity> 瞎推荐;
  private List<DayItemEntity> 福利;

  public List<DayItemEntity> getAndroid() {
    return Android;
  }

  public void setAndroid(List<DayItemEntity> Android) {
    this.Android = Android;
  }

  public List<DayItemEntity> getIOS() {
    return iOS;
  }

  public void setIOS(List<DayItemEntity> iOS) {
    this.iOS = iOS;
  }

  public List<DayItemEntity> get休息视频() {
    return 休息视频;
  }

  public void set休息视频(List<DayItemEntity> 休息视频) {
    this.休息视频 = 休息视频;
  }

  public List<DayItemEntity> get拓展资源() {
    return 拓展资源;
  }

  public void set拓展资源(List<DayItemEntity> 拓展资源) {
    this.拓展资源 = 拓展资源;
  }

  public List<DayItemEntity> get瞎推荐() {
    return 瞎推荐;
  }

  public void set瞎推荐(List<DayItemEntity> 瞎推荐) {
    this.瞎推荐 = 瞎推荐;
  }

  public List<DayItemEntity> get福利() {
    return 福利;
  }

  public void set福利(List<DayItemEntity> 福利) {
    this.福利 = 福利;
  }

  public static class DayItemEntity {
    /**
     * _id : 56cc6d23421aa95caa707a69
     * createdAt : 2015-08-06T07:15:52.65Z
     * desc : 类似Link Bubble的悬浮式操作设计
     * publishedAt : 2015-08-07T03:57:48.45Z
     * type : Android
     * url : https://github.com/recruit-lifestyle/FloatingView
     * used : true
     * who : mthli
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
      return _id;
    }

    public void set_id(String _id) {
      this._id = _id;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public String getPublishedAt() {
      return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
      this.publishedAt = publishedAt;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public boolean isUsed() {
      return used;
    }

    public void setUsed(boolean used) {
      this.used = used;
    }

    public String getWho() {
      return who;
    }

    public void setWho(String who) {
      this.who = who;
    }
  }
}
