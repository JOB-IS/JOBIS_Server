package com.jobis.web.dto.response;

import com.jobis.domain.code.RecruitmentScheduleStatus;
import com.jobis.domain.code.RecruitmentScheduleType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecruitmentScheduleResponseDTO {

  private Long id;
  private String name;
  private String description;
  private String body;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private LocalDate startDate;
  private LocalDate endDate;
  private RecruitmentScheduleType recruitmentScheduleType;
  private RecruitmentScheduleStatus recruitmentScheduleStatus;

  // recruitment
  private Long recruitmentId;

  @QueryProjection
  public RecruitmentScheduleResponseDTO(Long id, String name, String description,
      String body, LocalDateTime startDateTime, LocalDateTime endDateTime,
      LocalDate startDate, LocalDate endDate,
      RecruitmentScheduleType recruitmentScheduleType,
      RecruitmentScheduleStatus recruitmentScheduleStatus, Long recruitmentId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.body = body;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.startDate = startDate;
    this.endDate = endDate;
    this.recruitmentScheduleType = recruitmentScheduleType;
    this.recruitmentScheduleStatus = recruitmentScheduleStatus;
    this.recruitmentId = recruitmentId;
  }
}
