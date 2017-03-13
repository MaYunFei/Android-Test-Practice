package io.yunfei.github.m3u8.parser;

import io.yunfei.github.m3u8.data.ExtInfo;
import io.yunfei.github.m3u8.data.Playlist;
import io.yunfei.github.m3u8.data.Track;
import io.yunfei.github.m3u8.scanner.M3U8ItemScanner;
import io.yunfei.github.m3u8.util.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yunfei on 2017/3/7.
 * email mayunfei6@gmail.com
 */

public class M3U8Parser {
  protected final M3U8ItemScanner.Encoding encoding;
  private final M3U8ItemScanner m3U8ItemScanner;

  public M3U8Parser(final InputStream inputStream, final M3U8ItemScanner.Encoding encoding) {
    this.encoding = encoding;
    this.m3U8ItemScanner = new M3U8ItemScanner(inputStream, encoding);
  }

  public Playlist parse() throws IOException, ParseException {

    final Playlist playlist = new Playlist();
    final ExtInfoParser extInfoParser = new ExtInfoParser();
    Track track;
    ExtInfo extInfo;
    final List<Track> trackList = new LinkedList<>();

    //this is to remove the first #EXTM3U line
    m3U8ItemScanner.next();

    while (m3U8ItemScanner.hasNext()) {
      final String m3UItemString = m3U8ItemScanner.next();

      final String[] m3U8ItemStringArray = m3UItemString.split(Constants.NEW_LINE_CHAR);
      track = new Track();
      extInfo = extInfoParser.parse(getExtInfLine(m3U8ItemStringArray));
      track.setExtInfo(extInfo);
      track.setUrl(getTrackUrl(m3U8ItemStringArray));
      trackList.add(track);
      System.out.println(track.getUrl());
    }

    Map<String, Set<Track>> trackSetMap = new LinkedHashMap<>();

    Set<Track> trackSet = new LinkedHashSet<>();


    for (Track track1 : trackList) {
      trackSet.add(track1);
    }
    trackSetMap.put(trackList.get(0).getExtInfo().getGroupTitle(),trackSet);

    //final Map<String, Set<Track>> trackSetMap = trackList.stream()
    //    .collect(Collectors.groupingBy(t -> t.getExtInfo().getGroupTitle(), Collectors.toSet()));

    playlist.setTrackSetMap(trackSetMap);

    return playlist;
  }

  private String getExtInfLine(final String[] m3uItemStringArray) {
    return m3uItemStringArray[0];
  }

  private String getTrackUrl(final String[] m3uItemStringArray) {
    return m3uItemStringArray[1];
  }
}
