package com.jobis.web.dto.request.recruitment;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import com.jobis.web.dto.request.recruitment.sub.CreateRecruitmentScheduleByRecruitmentRequestDTO;
import java.util.List;
import javax.validation.Valid;
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
  private String color;
  @NotNull
  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

  @Valid
  private List<CreateRecruitmentScheduleByRecruitmentRequestDTO> recruitmentScheduleList;

}
