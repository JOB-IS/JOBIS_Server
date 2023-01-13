package com.jobis.web.dto.request.holiday;

import com.jobis.web.dto.request.PagingDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class HolidaySearchRequestDTO extends PagingDTO {

  private Boolean holiday;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate fromDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate toDate;

}
