package com.jobis.web.service;

import com.jobis.domain.entity.Recruitment;
import com.jobis.domain.entity.RecruitmentSchedule;
import com.jobis.domain.repository.recruitment.RecruitmentRepository;
import com.jobis.domain.repository.recruitmentSchedule.RecruitmentScheduleRepository;
import com.jobis.domain.repository.recruitmentSchedule.param.SearchRecruitmentScheduleParam;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.exception.ResourceNotFoundException;
import com.jobis.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode;
import com.jobis.web.dto.request.recruitmentschedule.CreateRecruitmentScheduleRequestDTO;
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
  private final RecruitmentRepository recruitmentRepository;

  public Page<RecruitmentScheduleResponseDTO> getRecruitmentSchedules(long userId,
      RecruitmentScheduleSearchRequestDTO dto) {
    Page<RecruitmentScheduleResponseDTO> page = recruitmentScheduleRepository.findAll(
        SearchRecruitmentScheduleParam.valueOf(dto, userId),
        PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

  @Transactional
  public Boolean createRecuitmentSchedule(long userId, CreateRecruitmentScheduleRequestDTO dto) {

    Recruitment recruitment = null;
    if (dto.getRecruitmentId() != null) {
      recruitment = recruitmentRepository.findById(dto.getRecruitmentId()).orElseThrow(
          () -> new ResourceNotFoundException(ResourceNotFoundExceptionCode.RECRUITMENT_NOT_FOUND));
      if (recruitment.getUser().getId() != userId) {
        throw new AuthenticationException(AuthenticationExceptionCode.NOT_AUTHENTICATED);
      }
    }

    RecruitmentSchedule recruitmentSchedule = new RecruitmentSchedule();
    recruitmentSchedule.setName(dto.getName());
    recruitmentSchedule.setDescription(dto.getDescription());
    recruitmentSchedule.setBody(dto.getBody());
    recruitmentSchedule.setStartDateTime(dto.getStartDateTime());
    recruitmentSchedule.setEndDateTime(dto.getEndDateTime());
    recruitmentSchedule.setStartDate(dto.getStartDate());
    recruitmentSchedule.setEndDate(dto.getEndDate());
    recruitmentSchedule.setRecruitmentScheduleType(dto.getRecruitmentScheduleType());
    recruitmentSchedule.setRecruitmentScheduleStatus(dto.getRecruitmentScheduleStatus());
    recruitmentSchedule.setRecruitment(recruitment);
    recruitmentScheduleRepository.save(recruitmentSchedule);

    return true;
  }

}
