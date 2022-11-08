package com.jobis.config.security;

import com.jobis.domain.entity.User;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {

  private final String email;
  private final String oauthId;
  private final Long userId;
  private final String nickName;

  public LoginUser(User user) {
    super(user.getEmail(), user.getOauthId(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    this.email = user.getEmail();
    this.oauthId = user.getOauthId();
    this.userId = user.getId();
    this.nickName = user.getNickName();
  }

}
