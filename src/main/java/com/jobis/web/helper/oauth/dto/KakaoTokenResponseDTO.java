package com.jobis.web.helper.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoTokenResponseDTO {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
//  private String tokenType;
//  private Long expiresIn;
//  private Long refreshTokenExpiresIn;

}
