package com.jobis.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HolidayType {

  NATIONAL_HOLIDAY("국경일", 1),
  ANNIVERSARY("기념일", 2),
  SEASON("24절기", 3),
  EXCLUDED_SEASON("잡절", 4);

  private final String description;
  private final Integer dateKind;

}
