package com.jobis.web.dto.request.recruitment;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import com.jobis.web.dto.request.PagingDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentSearchRequestDTO extends PagingDTO {

  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

}