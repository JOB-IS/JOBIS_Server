package com.jobis.web.service;

import com.jobis.domain.entity.Recruitment;
import com.jobis.domain.entity.User;
import com.jobis.domain.repository.recruitment.RecruitmentRepository;
import com.jobis.domain.repository.recruitment.param.SearchRecruitmentParam;
import com.jobis.domain.repository.user.UserRepository;
import com.jobis.exception.ResourceNotFoundException;
import com.jobis.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode;
import com.jobis.web.dto.request.recruitment.CreateRecruitmentRequestDTO;
import com.jobis.web.dto.request.recruitment.RecruitmentSearchRequestDTO;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentService {

  private final RecruitmentRepository recruitmentRepository;
  private final UserRepository userRepository;

  public Page<RecruitmentResponseDTO> getRecruitments(RecruitmentSearchRequestDTO dto,
      long userId) {
    Page<RecruitmentResponseDTO> page = recruitmentRepository.findAll(
        SearchRecruitmentParam.valueOf(dto, userId),
        PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

  @Transactional
  public RecruitmentResponseDTO createRecruitment(CreateRecruitmentRequestDTO dto, long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
        ResourceNotFoundExceptionCode.USER_NOT_FOUND));

    Recruitment recruitment = new Recruitment();
    recruitment.setName(dto.getName());
    recruitment.setDescription(dto.getDescription());
    recruitment.setBody(dto.getBody());
    recruitment.setPosition(dto.getPosition());
    recruitment.setLink(dto.getLink());
    recruitment.setRecruitmentStatus(dto.getRecruitmentStatus());
    recruitment.setWorkType(dto.getWorkType());
    recruitment.setUser(user);
    recruitmentRepository.save(recruitment);

    return RecruitmentResponseDTO.valueOf(recruitment);
  }

}