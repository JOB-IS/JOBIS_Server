package com.jobis.web.dto.response;

import com.jobis.domain.code.AuthType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDTO {

  private String accessToken;
  private String refreshToken;
  private AuthType authType;
  private long accessTokenExpireTime;
  private long refreshTokenExpireTime;

}
