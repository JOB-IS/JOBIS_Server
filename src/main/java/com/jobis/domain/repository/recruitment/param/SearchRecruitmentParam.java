package com.jobis.domain.repository.recruitment.param;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import com.jobis.web.dto.request.recruitment.RecruitmentSearchRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRecruitmentParam {

  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;
  private Long userId;

  public static SearchRecruitmentParam valueOf(RecruitmentSearchRequestDTO dto, Long userId) {
    SearchRecruitmentParam param = new SearchRecruitmentParam();
    param.setRecruitmentStatus(dto.getRecruitmentStatus());
    param.setWorkType(dto.getWorkType());
    param.setUserId(userId);
    return param;
  }

}
