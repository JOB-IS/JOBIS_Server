package com.catchup.web.dto.request;

import com.catchup.domain.code.OauthProviderType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
  @NotEmpty
  private String oauthId;
  @NotEmpty
  private String nickName;
  @NotNull
  private OauthProviderType oauthProviderType;
}
