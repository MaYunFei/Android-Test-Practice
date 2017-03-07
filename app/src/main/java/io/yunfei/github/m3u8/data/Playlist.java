package io.yunfei.github.m3u8.data;

import java.util.Map;
import java.util.Set;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class Playlist {
  private Map<String, Set<Track>> trackSetMap;

  public Map<String, Set<Track>> getTrackSetMap() {
    return trackSetMap;
  }

  public void setTrackSetMap(Map<String, Set<Track>> trackSetMap) {
    this.trackSetMap = trackSetMap;
  }
}
