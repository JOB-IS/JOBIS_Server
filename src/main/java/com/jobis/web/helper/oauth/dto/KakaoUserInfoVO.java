package com.jobis.web.helper.oauth.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoVO {

  private Long id;
  private kakao_account kakao_account;

  @Getter
  public class kakao_account {
    private String email;
    private String name;
    private profile profile;
  }

  @Getter
  public static class profile {
    private String nickname;
    private String profile_image_url;
  }
}
