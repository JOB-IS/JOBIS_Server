package com.jobis.web.dto.request.recruitment;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRecruitmentRequestDTO {

  private String name;
  private String description;
  private String body;
  private String companyName;
  private String position;
  private String link;
  @NotNull
  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

}
