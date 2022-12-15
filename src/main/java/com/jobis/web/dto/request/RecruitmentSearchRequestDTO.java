package com.jobis.web.dto.request;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentSearchRequestDTO extends PagingDTO {

  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

}