package com.jobis.web.service;

import com.jobis.config.security.LoginUser;
import com.jobis.config.security.TokenProvider;
import com.jobis.domain.entity.User;
import com.jobis.domain.repository.UserRepository;
import com.jobis.web.dto.request.LoginRequestDTO;
import com.jobis.web.dto.response.TokenResponseDTO;
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

  // TODO - DELETE
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

    // TODO 추후 {이메일, oauthId}로 변경?
    // 로그인
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getOauthId(), passwordEncoder.encode(dto.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰 생성
    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

}
