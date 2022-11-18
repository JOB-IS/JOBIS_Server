package com.jobis.web.helper.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.web.helper.oauth.dto.OauthUserInfoVO;
import com.jobis.web.helper.oauth.dto.KakaoTokenResponseDTO;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OauthHelper {

  @Value("${auth.kakao.client_id}")
  private String KAKAO_CLIENT_ID;
  @Value("${auth.kakao.client_secret}")
  private String KAKAO_CLIENT_SECRET;
  @Value("${auth.kakao.redirect_url}")
  private String KAKAO_REDIRECT_URL;
  @Value("${auth.kakao.url.access-token}")
  private String KAKAO_URL_FOR_ACCESS_TOKEN;
  @Value("${auth.kakao.url.user-info}")
  private String KAKAO_URL_FOR_USER_INFO;

  @Value("${auth.google.client_id}")
  private String GOOGLE_CLIENT_ID;
  @Value("${auth.google.client_secret}")
  private String GOOGLE_CLIENT_SECRET;
  @Value("${auth.google.redirect_url}")
  private String GOOGLE_REDIRECT_URL;
  @Value("${auth.google.url.access-token}")
  private String GOOGLE_URL_FOR_ACCESS_TOKEN;
  @Value("${auth.google.url.user-info}")
  private String GOOGLE_URL_FOR_USER_INFO;


  private final RestTemplate restTemplate;

  public KakaoTokenResponseDTO getAccessTokenFromGoogle(String code) {
    MultiValueMap<String, String> requestObject = new LinkedMultiValueMap<>();
    requestObject.put("grant_type", Collections.singletonList("authorization_code"));
    requestObject.put("client_id", Collections.singletonList(GOOGLE_CLIENT_ID));
    requestObject.put("client_secret", Collections.singletonList(GOOGLE_CLIENT_SECRET));
    requestObject.put("redirect_uri", Collections.singletonList(GOOGLE_REDIRECT_URL));
    requestObject.put("code", Collections.singletonList(code));

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestObject, headers);

      ResponseEntity<KakaoTokenResponseDTO> response = restTemplate.postForEntity(
          GOOGLE_URL_FOR_ACCESS_TOKEN, entity, KakaoTokenResponseDTO.class);
      return response.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      return null;
    }
  }

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

  public KakaoTokenResponseDTO getAccessTokenFromKakao(String code) {
    MultiValueMap<String, String> requestObject = new LinkedMultiValueMap<>();
    requestObject.put("grant_type", Collections.singletonList("authorization_code"));
    requestObject.put("client_id", Collections.singletonList(KAKAO_CLIENT_ID));
    requestObject.put("client_secret", Collections.singletonList(KAKAO_CLIENT_SECRET));
    requestObject.put("redirect_uri", Collections.singletonList(KAKAO_REDIRECT_URL));
    requestObject.put("code", Collections.singletonList(code));

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestObject, headers);

      ResponseEntity<KakaoTokenResponseDTO> response = restTemplate.postForEntity(
          KAKAO_URL_FOR_ACCESS_TOKEN, entity, KakaoTokenResponseDTO.class);
      return response.getBody();
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
