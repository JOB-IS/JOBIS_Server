package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.web.dto.request.RecruitmentSearchRequestDTO;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import com.jobis.web.service.RecruitmentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {

  private final RecruitmentService recruitmentService;

  @GetMapping
  public ResponseEntity<Page<RecruitmentResponseDTO>> getRecruitments(
      @AuthenticationPrincipal LoginUser loginUser, @Valid RecruitmentSearchRequestDTO dto) {
    Page<RecruitmentResponseDTO> responseDTO = recruitmentService
        .getRecruitments(dto, loginUser.getUserId());
    return ResponseEntity.ok(responseDTO);
  }

}
