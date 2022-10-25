package com.catchup.web.controller;

import com.catchup.web.dto.request.LoginRequestDTO;
import com.catchup.web.dto.response.TokenResponseDTO;
import com.catchup.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public TokenResponseDTO login(@RequestBody LoginRequestDTO dto) {
    return authService.login(dto);
  }

}
