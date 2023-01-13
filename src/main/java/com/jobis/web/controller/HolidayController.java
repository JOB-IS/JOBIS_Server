package com.jobis.web.controller;

import com.jobis.config.security.LoginUser;
import com.jobis.web.dto.request.holiday.HolidaySearchRequestDTO;
import com.jobis.web.dto.response.HolidayResponseDTO;
import com.jobis.web.service.HolidayService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

  private final HolidayService holidayService;

  @GetMapping
  public ResponseEntity<Page<HolidayResponseDTO>> getHolidays(
      @AuthenticationPrincipal LoginUser loginUser, @Valid HolidaySearchRequestDTO dto) {
    Page<HolidayResponseDTO> page = holidayService.getHolidays(dto);
    return ResponseEntity.ok(page);
  }

}
