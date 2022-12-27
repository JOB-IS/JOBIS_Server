package com.jobis.domain.repository.recruitmentSchedule.param;

import com.jobis.domain.code.RecruitmentScheduleStatus;
import com.jobis.domain.code.RecruitmentScheduleType;
import com.jobis.web.dto.request.recruitmentschedule.RecruitmentScheduleSearchRequestDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRecruitmentScheduleParam {

  private LocalDate fromDate;
  private LocalDate toDate;
  private RecruitmentScheduleType recruitmentScheduleType;
  private RecruitmentScheduleStatus recruitmentScheduleStatus;
  private Long userId;

  public static SearchRecruitmentScheduleParam valueOf(RecruitmentScheduleSearchRequestDTO dto,
      Long userId) {
    SearchRecruitmentScheduleParam param = new SearchRecruitmentScheduleParam();
    param.setFromDate(dto.getFromDate());
    param.setToDate(dto.getToDate());
    param.setRecruitmentScheduleType(dto.getRecruitmentScheduleType());
    param.setRecruitmentScheduleStatus(dto.getRecruitmentScheduleStatus());
    param.setUserId(userId);
    return param;
  }

}
