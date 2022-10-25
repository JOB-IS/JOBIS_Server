package com.catchup.config.security;

import com.catchup.domain.entity.User;
import com.catchup.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserRepository userRepository;
  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


  protected void additionalAuthenticationChecks(UserDetails userDetails,
      CustomAbstractAuthenticationToken authentication) throws SecurityException {

    if (authentication.getCredentials() == null) {
//      log.debug("additionalAuthenticationChecks is null !");
      throw new SecurityException("NOT_FOUND_PASSWORD");
    }
    String presentedPassword = authentication.getCredentials().toString();

    if (!presentedPassword.equals(userDetails.getPassword())) {
      throw new SecurityException("WRONG_PASSWORD");
    }
  }

  @Override
  public Authentication authenticate(Authentication authentication)
      throws InternalAuthenticationServiceException {
    UserDetails user = null;
    try {
      User userEntity = userRepository.findByNickName(authentication.getName())
          .orElseThrow(() -> new InternalAuthenticationServiceException(
              "No User NickName"));

      List<SimpleGrantedAuthority> authList = new ArrayList<>();
      authList.add(new SimpleGrantedAuthority(userEntity.getAuthorityType().getValue()));

      user = new org.springframework.security.core.userdetails.User(
          userEntity.getNickName(),
          userEntity.getOauthId(),
          authList
      );
    } catch (Exception e) {
      throw new InternalAuthenticationServiceException("내부 인증 로직 오류");
    }

    Object principalToReturn = user;
    CustomAbstractAuthenticationToken result = new CustomAbstractAuthenticationToken(
        principalToReturn, authentication.getCredentials(),
        this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
    additionalAuthenticationChecks(user, result);
    result.setDetails(authentication.getDetails());
    return result;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(CustomAbstractAuthenticationToken.class);
  }
}