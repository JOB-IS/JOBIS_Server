package com.catchup.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDTO {

  private String accessToken;
  private String refreshToken;

}
