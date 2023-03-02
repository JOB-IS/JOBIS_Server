package com.jobis.web.service;

import com.jobis.config.security.LoginUser;
import com.jobis.config.security.TokenProvider;
import com.jobis.domain.code.AuthType;
import com.jobis.domain.code.OauthProviderType;
import com.jobis.domain.entity.User;
import com.jobis.domain.repository.user.UserRepository;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.exception.ResourceNotFoundException;
import com.jobis.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode;
import com.jobis.web.dto.request.AddAdditionalInfoRequestDTO;
import com.jobis.web.dto.request.OauthLoginRequestDTO;
import com.jobis.web.dto.request.ReIssueRequestDTO;
import com.jobis.web.dto.response.TokenResponseDTO;
import com.jobis.web.helper.oauth.OauthHelper;
import com.jobis.web.helper.oauth.dto.KakaoUserInfoVO;
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

  @Transactional
  public TokenResponseDTO kakaoOauthLogin(OauthLoginRequestDTO dto) {
    // user 정보 요청
    KakaoUserInfoVO kakaoUserInfoVO = oauthHelper.getUserInfoFromKakao(dto.getAccessToken());

    // 가입 여부 확인
    User user = userRepository.findByOauthId(kakaoUserInfoVO.getId().toString()).orElse(null);

    // PRE_USER 신규 등록
    if (user == null) {
      user = new User();
      user.setEmail(kakaoUserInfoVO.getKakao_account().getEmail());
      user.setOauthId(kakaoUserInfoVO.getId().toString());
      user.setOauthProviderType(OauthProviderType.KAKAO);
      user.setAuthType(AuthType.ROLE_PRE_USER);
      userRepository.save(user);
    }

    // 로그인 처리
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(user.getId(),
            passwordEncoder.encode(user.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

  @Transactional
  public TokenResponseDTO googleOauthLogin(OauthLoginRequestDTO dto) {
    // user 정보 요청
    GoogleUserInfoVO googleUserInfoVO = oauthHelper.getUserInfoFromGoogle(dto.getAccessToken());

    // 가입 여부 확인
    User user = userRepository.findByOauthId(googleUserInfoVO.getId()).orElse(null);

    // PRE_USER 신규 등록
    if (user == null) {
      user = new User();
      user.setEmail(googleUserInfoVO.getEmail());
      user.setOauthId(googleUserInfoVO.getId());
      user.setOauthProviderType(OauthProviderType.GOOGLE);
      user.setAuthType(AuthType.ROLE_PRE_USER);
      userRepository.save(user);
    }

    // 로그인 처리
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(user.getId(),
            passwordEncoder.encode(user.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

  @Transactional
  public TokenResponseDTO addAdditionalInfo(AddAdditionalInfoRequestDTO dto, long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
        ResourceNotFoundExceptionCode.USER_NOT_FOUND));

    // nickName 중복 검사
    if (userRepository.existsByNickName(dto.getNickName())) {
      throw new AuthenticationException(AuthenticationExceptionCode.DUPLICATE_NICKNAME);
    }

    // USER 변경
    user.setNickName(dto.getNickName());
    user.setAuthType(AuthType.ROLE_USER);
    userRepository.save(user);

    // 로그인 처리
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(user.getId(),
            passwordEncoder.encode(user.getOauthId())));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.createToken((LoginUser) authentication.getPrincipal());
  }

  public TokenResponseDTO reIssueToken(ReIssueRequestDTO dto) {
    tokenProvider.validateToken(dto.getRefreshToken());

    LoginUser loginUser = (LoginUser) tokenProvider.getAuthentication(dto.getAccessToken()).getPrincipal();

    return tokenProvider.createToken(loginUser);
  }

}
