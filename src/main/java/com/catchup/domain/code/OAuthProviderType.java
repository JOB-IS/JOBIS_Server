package com.catchup.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProviderType {

  GOOGLE("GOOGLE");

  private final String value;

}
