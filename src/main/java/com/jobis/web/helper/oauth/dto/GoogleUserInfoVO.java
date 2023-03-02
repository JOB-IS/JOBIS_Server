package com.jobis.web.helper.oauth.dto;


import lombok.Getter;

@Getter
public class GoogleUserInfoVO {

  private String id;
  private String email;
  private String name;
  private String picture;
  private String locale;

}