package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentScheduleType {

  APPLY("지원"),
  DOCUMENT("서류제출"),
  INTERVIEW("인터뷰"),
  ASSIGNMENT("과제"),
  TEST("시험"),
  MEDICAL_CHECK("채용검진"),
  ANNOUNCEMENT("결과 발표"),
  CONTRACT("입사 계약"),
  ETC("기타");

  private final String description;

}
