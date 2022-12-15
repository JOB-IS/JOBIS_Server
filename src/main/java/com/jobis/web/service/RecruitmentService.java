package com.jobis.web.service;

import com.jobis.domain.repository.recruitment.RecruitmentRepository;
import com.jobis.domain.repository.recruitment.param.SearchRecruitmentParam;
import com.jobis.web.dto.request.RecruitmentSearchRequestDTO;
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

  public Page<RecruitmentResponseDTO> getRecruitments(RecruitmentSearchRequestDTO dto,
      long userId) {
    Page<RecruitmentResponseDTO> page = recruitmentRepository.findAll(
        SearchRecruitmentParam.valueOf(dto, userId),
            PageRequest.of(dto.getPageNumber(), dto.getRowCount()));
    return page;
  }

}