package com.jobis.domain.repository.holiday.param;

import com.jobis.web.dto.request.holiday.HolidaySearchRequestDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchHolidayParam {

  private Boolean holiday;
  private LocalDate fromDate;
  private LocalDate toDate;

  public static SearchHolidayParam valueOf(HolidaySearchRequestDTO dto) {
    SearchHolidayParam param = new SearchHolidayParam();
    param.setHoliday(dto.getHoliday());
    param.setFromDate(dto.getFromDate());
    param.setToDate(dto.getToDate());
    return param;
  }

}
