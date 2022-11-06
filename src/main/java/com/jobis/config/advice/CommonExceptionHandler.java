package com.jobis.config.advice;

import com.jobis.exception.CommonException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
  @ExceptionHandler(value = CommonException.class)
  public ExceptionResponse defaultErrorHandler(HttpServletRequest req, CommonException e) {
    // TODO Logging - 예상된 예외
    return new ExceptionResponse(e.getResponseCode().getCode(), e.getMessage());
  }

}
