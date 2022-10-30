package com.catchup.web.controller;

import com.catchup.config.security.LoginUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

  @GetMapping
  public String hello(@AuthenticationPrincipal LoginUser loginUser) {
    String nickName = loginUser.getNickName();
    return "hello " + nickName + " " + loginUser.getAuthorities();
  }

}
