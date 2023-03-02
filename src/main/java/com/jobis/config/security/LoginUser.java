package com.jobis.config.security;

import com.jobis.domain.code.AuthType;
import com.jobis.domain.entity.User;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {

  private final Long userId;
  private final String oauthId;
  private final String email;
  private final AuthType authType;
  private final String nickName;

  public LoginUser(User user) {
    super(user.getId().toString(), user.getOauthId(), Collections.singleton(new SimpleGrantedAuthority(user.getAuthType().getValue())));
    this.userId = user.getId();
    this.oauthId = user.getOauthId();
    this.email = user.getEmail();
    this.authType = user.getAuthType();
    this.nickName = user.getNickName();
  }
}