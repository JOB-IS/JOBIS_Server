package com.catchup.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponseDTO {
  private String grantType;
  private String accessToken;
  private String refreshToken;
}
