package com.catchup.web.service;

import com.catchup.config.security.CustomAbstractAuthenticationToken;
import com.catchup.config.security.TokenProvider;
import com.catchup.domain.code.AuthorityType;
import com.catchup.domain.entity.User;
import com.catchup.domain.repository.UserRepository;
import com.catchup.web.dto.request.LoginRequestDTO;
import com.catchup.web.dto.response.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

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
      user.setAuthorityType(AuthorityType.USER);
      userRepository.save(user);
    }

    // 로그인
    CustomAbstractAuthenticationToken customEmailPasswordAuthToken =
        new CustomAbstractAuthenticationToken(dto.getNickName(), dto.getOauthId());
    Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);

    // 토큰 생성
    String nickName = authenticate.getName();
    String accessToken = tokenProvider.createAccessToken(nickName,
        AuthorityType.USER);
    String refreshToken = tokenProvider.createRefreshToken(nickName,
        AuthorityType.USER);

    return tokenProvider.createTokenDTO(accessToken, refreshToken);
  }

}
