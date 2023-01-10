package com.jobis.web.dto.request.recruitment.sub;

import com.jobis.domain.code.RecruitmentScheduleStatus;
import com.jobis.domain.code.RecruitmentScheduleType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class CreateRecruitmentScheduleByRecruitmentRequestDTO {

  private String name;
  private String description;

  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate endDate;

  @NotNull
  private RecruitmentScheduleType recruitmentScheduleType;
  @NotNull
  private RecruitmentScheduleStatus recruitmentScheduleStatus;

}
