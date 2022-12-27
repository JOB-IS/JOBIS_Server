package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.web.dto.request.recruitmentschedule.RecruitmentScheduleSearchRequestDTO;
import com.jobis.web.dto.response.RecruitmentScheduleResponseDTO;
import com.jobis.web.service.RecruitmentScheduleService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruitment-schedules")
@RequiredArgsConstructor
public class RecruitmentScheduleController {

  private final RecruitmentScheduleService recruitmentScheduleService;

  @GetMapping
  public ResponseEntity<Page<RecruitmentScheduleResponseDTO>> getRecruitmentSchedules(
      @AuthenticationPrincipal LoginUser loginUser, @Valid RecruitmentScheduleSearchRequestDTO dto) {
    Page<RecruitmentScheduleResponseDTO> responseDTO = recruitmentScheduleService
        .getRecruitmentSchedules(loginUser.getUserId(), dto);
    return ResponseEntity.ok(responseDTO);
  }

}
