package com.jobis.web.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OauthLoginRequestDTO {

  @NotEmpty
  private String accessToken;

}
