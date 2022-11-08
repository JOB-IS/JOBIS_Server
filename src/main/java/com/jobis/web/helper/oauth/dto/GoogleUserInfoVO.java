package com.jobis.web.helper.oauth.dto;


import lombok.Getter;

@Getter
public class GoogleUserInfoVO {

  private String id;
  private String email;
  private String name;
//  private String givenName;
//  private String familyName;
//  private String picture;
//  private String locale;
//  private Boolean verifiedEmail;
}

//  "email": "wkdgus7113@gmail.com",
//  "verified_email": true,
//  "name": "장현희",
//  "given_name": "현희",
//  "family_name": "장",
//  "picture": "https://lh3.googleusercontent.com/a/ALm5wu1zcDhsS2MR2EhvFJQwwEMsjNVUKPAT0aU6CfLu=s96-c",
//  "locale": "ko"