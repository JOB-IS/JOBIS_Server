package com.jobis.config.security;

import com.jobis.domain.entity.User;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {

  private final Long id;
  private final String nickName;
  private final String oauthId;

  public LoginUser(User user) {
    super(user.getNickName(), user.getOauthId(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    this.id = user.getId();
    this.nickName = user.getNickName();
    this.oauthId = user.getOauthId();
  }

}
