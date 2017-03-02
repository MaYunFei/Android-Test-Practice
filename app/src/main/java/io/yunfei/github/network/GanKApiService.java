package io.yunfei.github.network;

import io.yunfei.github.entity.DayEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public interface GanKApiService {
  @GET("day/2015/08/06") Observable<BaseResponse<DayEntity>> getDayData();
}
