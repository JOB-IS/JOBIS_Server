package com.jobis.web.service;

import com.jobis.domain.repository.holiday.HolidayRepository;
import com.jobis.domain.repository.holiday.param.SearchHolidayParam;
import com.jobis.web.dto.request.holiday.HolidaySearchRequestDTO;
import com.jobis.web.dto.response.HolidayResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HolidayService {

  private final HolidayRepository holidayRepository;

  public Page<HolidayResponseDTO> getHolidays(HolidaySearchRequestDTO dto) {
    Page<HolidayResponseDTO> page = holidayRepository.findAll(
        SearchHolidayParam.valueOf(dto), PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

}
