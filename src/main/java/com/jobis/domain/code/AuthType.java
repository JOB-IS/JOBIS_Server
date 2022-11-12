package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {

  ROLE_USER("ROLE_USER", "USER"),
  ROLE_PRE_USER("ROLE_PRE_USER", "PRE_USER");  // 추가 정보 입력 필요 (가입 진행중)

  private final String value;
  private final String valueWithoutPrefix;

}
