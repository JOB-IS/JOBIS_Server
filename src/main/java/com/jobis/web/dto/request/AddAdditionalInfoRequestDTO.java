package com.jobis.web.dto.request;


import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AddAdditionalInfoRequestDTO {

  @NotEmpty
  private String nickName;

}
