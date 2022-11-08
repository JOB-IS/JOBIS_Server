package com.jobis.web.helper.oauth;

import com.jobis.web.helper.oauth.dto.GoogleUserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OauthHelper {

  @Value("${auth.google.url.user-info}")
  private String googleUrlForUserInfo;

  private final RestTemplate restTemplate;

  public GoogleUserInfoVO getUserInfoFromGoogle(String accessToken) {

    try {
      // TODO RestTemplate Util Class?
      ResponseEntity<GoogleUserInfoVO> apiResponseJson = restTemplate.getForEntity(
          googleUrlForUserInfo + "?access_token=" + accessToken,
          GoogleUserInfoVO.class);
      return apiResponseJson.getBody();
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
//      System.out.println(e.getStatusCode());
//      System.out.println(e.getResponseBodyAsString());
      return null;
    }

  }

}
