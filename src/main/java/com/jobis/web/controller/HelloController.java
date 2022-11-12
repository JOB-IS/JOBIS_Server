package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.exception.ResourceNotFoundException;
import com.jobis.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

  @GetMapping("/real-user")
  public String helloRealUser(@AuthenticationPrincipal LoginUser loginUser) {
    String nickName = loginUser.getNickName();
//    throw new ResourceNotFoundException(ResourceNotFoundExceptionCode.USER_NOT_FOUND);
    return "hello " + nickName + " " + loginUser.getAuthorities();
  }


  @GetMapping("/only-pre-user")
  public String onlyPreUser(@AuthenticationPrincipal LoginUser loginUser) {
    String nickName = loginUser.getNickName();
    return "hello " + nickName + " " + loginUser.getAuthorities();
  }
}
