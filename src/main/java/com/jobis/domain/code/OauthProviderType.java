package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthProviderType {

  GOOGLE("google"),
  KAKAO("kakao");

  private final String value;

}
