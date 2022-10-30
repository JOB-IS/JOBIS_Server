package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthProviderType {

  GOOGLE("GOOGLE");

  private final String value;

}
