package io.yunfei.github.network;

/**
 * Created by yunfei on 2017/3/2.
 * email mayunfei6@gmail.com
 */

public class BaseResponse<T> {
  boolean error;
  T results;

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public T getResults() {
    return results;
  }

  public void setResults(T results) {
    this.results = results;
  }
}
