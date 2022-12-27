package com.jobis.domain.repository.recruitmentSchedule;

import com.jobis.domain.repository.recruitmentSchedule.param.SearchRecruitmentScheduleParam;
import com.jobis.web.dto.response.RecruitmentScheduleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitmentScheduleRepositoryCustom {

  Page<RecruitmentScheduleResponseDTO> findAll(SearchRecruitmentScheduleParam param, Pageable pageable);

}
