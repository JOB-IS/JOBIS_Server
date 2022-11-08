package com.jobis.web.controller;

import com.jobis.web.dto.request.GoogleLoginRequestDTO;
import com.jobis.web.dto.request.LoginRequestDTO;
import com.jobis.web.dto.response.TokenResponseDTO;
import com.jobis.web.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.login(dto);
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping(value = "/google/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> loginWithGoogle(
      @Valid @RequestBody GoogleLoginRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.loginWithGoogle(dto);
    return ResponseEntity.ok(responseDTO);
  }

//  TODO NickName 입력받는 API 생성

}
