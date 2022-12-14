package com.jobis.web.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDTO {

  @Min(value = 0)
  private int pageNumber = 0;
  @Min(value = 1)
  @Max(value = 1000)
  private int rowCount = 20;

}
