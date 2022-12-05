package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentStatus {

  READY_TO_APPLY("ready to apply"),
  APPLIED("applied"),
  SCHEDULED("scheduled"),
  ARCHIVED("archived"),
  SIGNED("signed");

  private final String value;

}
