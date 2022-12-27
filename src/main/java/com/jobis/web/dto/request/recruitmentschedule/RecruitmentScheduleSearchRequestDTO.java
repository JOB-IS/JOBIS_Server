package com.jobis.web.dto.request.recruitmentschedule;

import com.jobis.domain.code.RecruitmentScheduleStatus;
import com.jobis.domain.code.RecruitmentScheduleType;
import com.jobis.web.dto.request.PagingDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class RecruitmentScheduleSearchRequestDTO extends PagingDTO {

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate fromDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate toDate;
  private RecruitmentScheduleType recruitmentScheduleType;
  private RecruitmentScheduleStatus recruitmentScheduleStatus;
  private Long recruitmentId;

}
