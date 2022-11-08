package com.jobis.web.service;

import com.jobis.config.security.LoginUser;
import com.jobis.config.security.TokenProvider;
import com.jobis.domain.code.OauthProviderType;
import com.jobis.domain.entity.User;
import com.jobis.domain.repository.UserRepository;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.web.dto.request.GoogleLoginRequestDTO;
import com.jobis.web.dto.request.LoginRequestDTO;
import com.jobis.web.dto.response.TokenResponseDTO;
import com.jobis.web.helper.oauth.OauthHelper;
import com.jobis.web.helper.oauth.dto.GoogleUserInfoVO;
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
  private final OauthHelper oauthHelper;

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

    // 로그인
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getOauthId(), passwordEncoder.encode(dto.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰 생성
    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }


  @Transactional
  public TokenResponseDTO loginWithGoogle(GoogleLoginRequestDTO dto) {

    // user 정보 요청
    GoogleUserInfoVO vo = null;
    if (OauthProviderType.GOOGLE.equals(dto.getOauthProviderType())) {
      vo = oauthHelper.getUserInfoFromGoogle(dto.getAccessToken());
    }

    if (vo == null) {
      throw new AuthenticationException(AuthenticationExceptionCode.INVALID_OAUTH_TOKEN);
    }

    // 가입 여부 확인
    User user = userRepository.findByOauthId(vo.getId()).orElse(null);

    // 신규 가입 : JWT 발금해도 NickName 이 없기 때문에 권한 오류 반환
    if (user == null) {
      user = new User();
      user.setEmail(vo.getEmail());
      user.setOauthId(vo.getId());
      user.setOauthProviderType(OauthProviderType.GOOGLE);
      userRepository.save(user);
    }

    // 로그인 처리
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
            passwordEncoder.encode(user.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

}
