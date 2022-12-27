package com.jobis.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtil {

  public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate date) {
    return LocalDateTime.of(date, LocalTime.MIN);
  }

}
