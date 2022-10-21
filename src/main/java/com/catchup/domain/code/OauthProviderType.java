package com.catchup.domain.code;

public enum OauthProviderType {

  GOOGLE("GOOGLE");

  private String value;

  OauthProviderType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
