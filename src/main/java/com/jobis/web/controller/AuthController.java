package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.domain.code.OauthProviderType;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponseDTO> oauthLogin(
      @Valid @RequestBody OauthLoginRequestDTO dto) {
    TokenResponseDTO responseDTO = authService.oauthLogin(dto);
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

  @GetMapping(value = "/{o-auth-provider-type}/redirect")
  public ResponseEntity<TokenResponseDTO> oauthLoginV2(
      @PathVariable(value = "o-auth-provider-type") OauthProviderType oauthProviderType,
      @RequestParam(value = "code") String code) {
    TokenResponseDTO responseDTO = authService.oauthLoginV2(oauthProviderType, code);
    return ResponseEntity.ok(responseDTO);
  }

}
