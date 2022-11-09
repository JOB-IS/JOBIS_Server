package com.jobis.web.dto.request;

import com.jobis.domain.code.OauthProviderType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OauthLoginRequestDTO {

  @NotEmpty
  private String accessToken;
  @NotNull
  private OauthProviderType oauthProviderType;

}
