package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkType {

  FULL_TIME("정규직"),
  TEMPORARY("비정규직"),
  CONTRACT("계약직"),
  INTERN("인턴");

  private final String description;
}
