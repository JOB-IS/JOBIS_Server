package com.jobis.web.service;

import com.jobis.domain.repository.recruitmentSchedule.RecruitmentScheduleRepository;
import com.jobis.domain.repository.recruitmentSchedule.param.SearchRecruitmentScheduleParam;
import com.jobis.web.dto.request.recruitmentschedule.RecruitmentScheduleSearchRequestDTO;
import com.jobis.web.dto.response.RecruitmentScheduleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentScheduleService {

  private final RecruitmentScheduleRepository recruitmentScheduleRepository;

  public Page<RecruitmentScheduleResponseDTO> getRecruitmentSchedules(long userId,
      RecruitmentScheduleSearchRequestDTO dto) {
    Page<RecruitmentScheduleResponseDTO> page = recruitmentScheduleRepository.findAll(
        SearchRecruitmentScheduleParam.valueOf(dto, userId),
        PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

}
