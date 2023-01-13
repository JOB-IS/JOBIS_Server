package com.jobis.web.dto.response;

import com.jobis.domain.code.HolidayType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HolidayResponseDTO {

  private Long id;
  private String dateName;
  private boolean holiday;
  private LocalDate date;
  private HolidayType holidayType;

  @QueryProjection
  public HolidayResponseDTO(Long id, String dateName, boolean holiday, LocalDate date, HolidayType holidayType) {
    this.id = id;
    this.dateName = dateName;
    this.holiday = holiday;
    this.date = date;
    this.holidayType = holidayType;
  }

}
