package com.catchup.web.dto.request;

import com.catchup.domain.code.OauthProviderType;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
  private String oauthId;
  private String nickName;
  private OauthProviderType oauthProviderType;
}
