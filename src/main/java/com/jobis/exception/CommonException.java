package com.jobis.exception;

import com.jobis.config.advice.ResponseCode;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class CommonException extends RuntimeException {

  private ResponseCode responseCode;

  public CommonException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

  public CommonException(ResponseCode responseCode, String detailMessage) {
    super(responseCode.getMessage() + " : " + detailMessage);
    this.responseCode = responseCode;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public static <T, R> Supplier<R> bind(Function<T, R> fn, T val) {
    return () -> fn.apply(val);
  }

}
