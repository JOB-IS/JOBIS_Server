package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.web.dto.request.recruitment.CreateRecruitmentRequestDTO;
import com.jobis.web.dto.request.recruitment.RecruitmentSearchRequestDTO;
import com.jobis.web.dto.request.recruitment.UpdateRecruitmentRequestDTO;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import com.jobis.web.service.RecruitmentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        .getRecruitments(loginUser.getUserId(), dto);
    return ResponseEntity.ok(responseDTO);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RecruitmentResponseDTO> createRecruitment(
      @AuthenticationPrincipal LoginUser loginUser,
      @Valid @RequestBody CreateRecruitmentRequestDTO dto) {
    RecruitmentResponseDTO responseDTO = recruitmentService
        .createRecruitment(loginUser.getUserId(), dto);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping(value = "/{recruitment-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> updateRecruitment(
      @AuthenticationPrincipal LoginUser loginUser,
      @PathVariable("recruitment-id") long recruitmentId,
      @RequestBody UpdateRecruitmentRequestDTO dto) {
    boolean responseDTO = recruitmentService
        .updateRecruitment(loginUser.getUserId(), recruitmentId, dto);
    return ResponseEntity.ok(responseDTO);
  }

}
