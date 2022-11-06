package com.jobis.exception;

import com.jobis.config.advice.ResponseCode;

public class ResourceNotFoundException extends CommonException {

  public enum ResourceNotFoundExceptionCode implements ResponseCode {
    RESOURCE_NOT_FOUND("RNF-001", "resource not found"),
    USER_NOT_FOUND("RNF-002", "user not found");

    private String code;
    private String message;

    ResourceNotFoundExceptionCode(String code, String message) {
      this.code = code;
      this.message = message;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }
  }

  public ResourceNotFoundException(ResourceNotFoundExceptionCode code) {
    super(code);
  }

  public ResourceNotFoundException(ResourceNotFoundExceptionCode code, String detailMessage) {
    super(code, detailMessage);
  }

  public ResourceNotFoundException(String detailMessage) {
    super(ResourceNotFoundExceptionCode.RESOURCE_NOT_FOUND, detailMessage);
  }
}
