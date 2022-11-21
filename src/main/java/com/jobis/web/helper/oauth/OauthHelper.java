package com.jobis.web.helper.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.web.helper.oauth.dto.OauthUserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

  public OauthUserInfoVO getUserInfoFromGoogle(String accessToken) {
    try {
      // TODO RestTemplate Util Class?
      ResponseEntity<OauthUserInfoVO> apiResponseJson = restTemplate.getForEntity(
          GOOGLE_URL_FOR_USER_INFO + "?access_token=" + accessToken,
          OauthUserInfoVO.class);
      return apiResponseJson.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      return null;
    }
  }

  public OauthUserInfoVO getUserInfoFromKakao(String accessToken) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.set("Authorization", "Bearer " + accessToken);

      HttpEntity entity = new HttpEntity<>(headers);

      ResponseEntity<String> response = restTemplate.postForEntity(
          KAKAO_URL_FOR_USER_INFO, entity, String.class);

      // Json Parsing
      JsonElement element = JsonParser.parseString(response.getBody());

      String uid = element.getAsJsonObject().get("id").getAsString();
      boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
      if (!hasEmail) {
        throw new AuthenticationException(AuthenticationExceptionCode.EMAIL_NOT_FOUND);
      }
      String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();

      OauthUserInfoVO vo = new OauthUserInfoVO();
      vo.setEmail(email);
      vo.setId(uid);
      return vo;
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      return null;
    }
  }

}
