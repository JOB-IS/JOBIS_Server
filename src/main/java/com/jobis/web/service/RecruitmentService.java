package com.jobis.web.service;

import com.jobis.domain.entity.Recruitment;
import com.jobis.domain.entity.RecruitmentSchedule;
import com.jobis.domain.entity.User;
import com.jobis.domain.repository.recruitment.RecruitmentRepository;
import com.jobis.domain.repository.recruitment.param.SearchRecruitmentParam;
import com.jobis.domain.repository.recruitmentSchedule.RecruitmentScheduleRepository;
import com.jobis.domain.repository.user.UserRepository;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.exception.ResourceNotFoundException;
import com.jobis.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode;
import com.jobis.web.dto.request.recruitment.CreateRecruitmentRequestDTO;
import com.jobis.web.dto.request.recruitment.RecruitmentSearchRequestDTO;
import com.jobis.web.dto.request.recruitment.UpdateRecruitmentRequestDTO;
import com.jobis.web.dto.request.recruitment.sub.CreateRecruitmentScheduleByRecruitmentRequestDTO;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentService {

  private final RecruitmentRepository recruitmentRepository;
  private final RecruitmentScheduleRepository recruitmentScheduleRepository;
  private final UserRepository userRepository;

  public Page<RecruitmentResponseDTO> getRecruitments(long userId,
      RecruitmentSearchRequestDTO dto) {
    Page<RecruitmentResponseDTO> page = recruitmentRepository.findAll(
        SearchRecruitmentParam.valueOf(dto, userId),
        PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

  @Transactional
  public RecruitmentResponseDTO createRecruitment(long userId, CreateRecruitmentRequestDTO dto) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
        ResourceNotFoundExceptionCode.USER_NOT_FOUND));

    Recruitment recruitment = new Recruitment();
    recruitment.setName(dto.getName());
    recruitment.setDescription(dto.getDescription());
    recruitment.setBody(dto.getBody());
    recruitment.setCompanyName(dto.getCompanyName());
    recruitment.setPosition(dto.getPosition());
    recruitment.setLink(dto.getLink());
    recruitment.setRecruitmentStatus(dto.getRecruitmentStatus());
    recruitment.setWorkType(dto.getWorkType());
    recruitment.setUser(user);
    recruitmentRepository.save(recruitment);

    List<RecruitmentSchedule> recruitmentScheduleList = new ArrayList<>();
    for (CreateRecruitmentScheduleByRecruitmentRequestDTO scheduleList :
        dto.getRecruitmentScheduleList()) {
      RecruitmentSchedule recruitmentSchedule = new RecruitmentSchedule();
      recruitmentSchedule.setName(scheduleList.getName());
      recruitmentSchedule.setDescription(scheduleList.getDescription());
      recruitmentSchedule.setStartDateTime(scheduleList.getStartDateTime());
      recruitmentSchedule.setEndDateTime(scheduleList.getEndDateTime());
      recruitmentSchedule.setStartDate(scheduleList.getStartDate());
      recruitmentSchedule.setEndDate(scheduleList.getEndDate());
      recruitmentSchedule.setRecruitmentScheduleType(scheduleList.getRecruitmentScheduleType());
      recruitmentSchedule.setRecruitmentScheduleStatus(scheduleList.getRecruitmentScheduleStatus());
      recruitmentSchedule.setRecruitment(recruitment);
      recruitmentScheduleList.add(recruitmentSchedule);
    }
    recruitmentScheduleRepository.saveAll(recruitmentScheduleList);

    return RecruitmentResponseDTO.valueOf(recruitment);
  }

  @Transactional
  public boolean updateRecruitment(long userId, long recruitmentId,
      UpdateRecruitmentRequestDTO dto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException(
            ResourceNotFoundExceptionCode.USER_NOT_FOUND));

    Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
        .orElseThrow(() -> new ResourceNotFoundException(
            ResourceNotFoundExceptionCode.RECRUITMENT_NOT_FOUND));

    if (!recruitment.getUser().equals(user)) {
      throw new AuthenticationException(AuthenticationExceptionCode.NOT_AUTHENTICATED);
    }

    if (StringUtils.hasText(dto.getName())) {
      recruitment.setName(dto.getName());
    }
    if (StringUtils.hasText(dto.getDescription())) {
      recruitment.setDescription(dto.getDescription());
    }
    if (StringUtils.hasText(dto.getBody())) {
      recruitment.setBody(dto.getBody());
    }
    if (StringUtils.hasText(dto.getPosition())) {
      recruitment.setPosition(dto.getPosition());
    }
    if (StringUtils.hasText(dto.getLink())) {
      recruitment.setLink(dto.getLink());
    }
    if (dto.getRecruitmentStatus() != null) {
      recruitment.setRecruitmentStatus(dto.getRecruitmentStatus());
    }
    if (dto.getWorkType() != null) {
      recruitment.setWorkType(dto.getWorkType());
    }
    recruitmentRepository.save(recruitment);

    return true;
  }

}