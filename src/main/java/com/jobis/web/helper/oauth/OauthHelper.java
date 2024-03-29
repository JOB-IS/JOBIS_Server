package com.jobis.web.helper.oauth;

import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.web.helper.oauth.dto.KakaoUserInfoVO;
import com.jobis.web.helper.oauth.dto.GoogleUserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OauthHelper {

  @Value("${auth.kakao.url.user-info}")
  private String KAKAO_URL_FOR_USER_INFO;
  @Value("${auth.google.url.user-info}")
  private String GOOGLE_URL_FOR_USER_INFO;

  private final RestTemplate restTemplate;

  public KakaoUserInfoVO getUserInfoFromKakao(String accessToken) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + accessToken);
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

      HttpEntity<String> request = new HttpEntity<String>(headers);
      ResponseEntity<KakaoUserInfoVO> response = restTemplate.exchange(KAKAO_URL_FOR_USER_INFO,
          HttpMethod.GET, request, KakaoUserInfoVO.class);
      return response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      throw new AuthenticationException(AuthenticationExceptionCode.INVALID_OAUTH_TOKEN);
    }
  }

  public GoogleUserInfoVO getUserInfoFromGoogle(String accessToken) {
    try {
      // TODO RestTemplate Util Class?
      ResponseEntity<GoogleUserInfoVO> apiResponseJson = restTemplate.getForEntity(
          GOOGLE_URL_FOR_USER_INFO + "?access_token=" + accessToken,
          GoogleUserInfoVO.class);
      return apiResponseJson.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      throw new AuthenticationException(AuthenticationExceptionCode.INVALID_OAUTH_TOKEN);
    }
  }

}
