package com.jobis.exception;

import com.jobis.config.advice.ResponseCode;

public class AuthenticationException extends CommonException {

  public enum AuthenticationExceptionCode implements ResponseCode {
    NOT_AUTHENTICATED("ATN-001", "not authenticated"),
    INVALID_OAUTH_TOKEN("ATN-002", "invalid oauth token"),
    INVALID_OAUTH_CODE("ATN-003", "invalid oauth code"),
    NEED_ADDITIONAL_INFO("ATN-004", "need additional information"),
    DUPLICATE_NICKNAME("ATN-005", "nickname is duplicated"),
    EMAIL_NOT_FOUND("ATN-006", "email not found");

    private String code;
    private String message;

    AuthenticationExceptionCode(String code, String message) {
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

  public AuthenticationException(AuthenticationExceptionCode code) {
    super(code);
  }

  public AuthenticationException(AuthenticationExceptionCode code, String detailMessage) {
    super(code, detailMessage);
  }

  public AuthenticationException(String detailMessage) {
    super(AuthenticationExceptionCode.NOT_AUTHENTICATED, detailMessage);
  }
}
