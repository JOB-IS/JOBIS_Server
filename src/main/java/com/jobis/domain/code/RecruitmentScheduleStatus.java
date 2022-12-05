package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentScheduleStatus {

  READY("대기"),
  PROCESSING("진행 중"),
  ARCHIVED("완료"),
  RETIRE("포기");

  private final String description;

}
