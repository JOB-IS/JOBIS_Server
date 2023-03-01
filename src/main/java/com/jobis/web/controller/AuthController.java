package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.web.dto.request.AddAdditionalInfoRequestDTO;
import com.jobis.web.dto.request.OauthLoginRequestDTO;
import com.jobis.web.dto.request.ReIssueRequestDTO;
import com.jobis.web.dto.response.TokenResponseDTO;
import com.jobis.web.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/login/oauth/kakao", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> kakaoOauthLogin(
      @Valid @RequestBody OauthLoginRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.kakaoOauthLogin(dto);
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping(value = "/login/oauth/google", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> googleOauthLogin(
      @Valid @RequestBody OauthLoginRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.googleOauthLogin(dto);
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping(value = "/additional-info", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> addAdditionalInfo(
      @AuthenticationPrincipal LoginUser loginUser,
      @Valid @RequestBody AddAdditionalInfoRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.addAdditionalInfo(dto, loginUser.getUserId());
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping(value = "/re-issue", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> reIssueToken(
      @Valid @RequestBody ReIssueRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.reIssueToken(dto);
    return ResponseEntity.ok(responseDTO);
  }

}
