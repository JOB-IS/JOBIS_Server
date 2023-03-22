package com.jobis.web.dto.request.recruitment;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRecruitmentRequestDTO {

  private String name;
  private String description;
  private String body;
  private String position;
  private String link;
  private String color;
  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

}
