package com.catchup.web.service;

import com.catchup.config.security.LoginUser;
import com.catchup.config.security.TokenProvider;
import com.catchup.domain.entity.User;
import com.catchup.domain.repository.UserRepository;
import com.catchup.web.dto.request.LoginRequestDTO;
import com.catchup.web.dto.response.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;

  // TODO - Refactoring
  //  https://oingdaddy.tistory.com/206
  //  https://onejunu.tistory.com/137
  @Transactional
  public TokenResponseDTO login(LoginRequestDTO dto) {
    // 가입 여부 확인
    User user = userRepository.findByOauthId(dto.getOauthId()).orElse(null);

    // 신규 가입
    if (user == null) {
      user = new User();
      user.setNickName(dto.getNickName());
      user.setOauthId(dto.getOauthId());
      user.setOauthProviderType(dto.getOauthProviderType());
      userRepository.save(user);
    }

    // 로그인
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getNickName(), passwordEncoder.encode(dto.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰 생성
    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

}
