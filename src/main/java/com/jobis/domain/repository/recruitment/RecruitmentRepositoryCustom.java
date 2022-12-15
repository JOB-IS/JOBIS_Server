package com.jobis.domain.repository.recruitment;

import com.jobis.domain.repository.recruitment.param.SearchRecruitmentParam;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitmentRepositoryCustom {

  Page<RecruitmentResponseDTO> findAll(SearchRecruitmentParam param, Pageable pageable);

}
