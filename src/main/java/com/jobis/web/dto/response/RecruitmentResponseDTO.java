package com.jobis.web.dto.response;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import com.jobis.domain.entity.Recruitment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecruitmentResponseDTO {

  private Long id;
  private String name;
  private String description;
  private String body;
  private String companyName;
  private String position;
  private String link;
  private RecruitmentStatus recruitmentStatus;
  private WorkType workType;

  @QueryProjection
  public RecruitmentResponseDTO(Long id, String name, String description, String body,
      String companyName, String position, String link,
      RecruitmentStatus recruitmentStatus, WorkType workType) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.body = body;
    this.companyName = companyName;
    this.position = position;
    this.link = link;
    this.recruitmentStatus = recruitmentStatus;
    this.workType = workType;
  }

  public static RecruitmentResponseDTO valueOf(Recruitment recruitment) {
    RecruitmentResponseDTO responseDTO = new RecruitmentResponseDTO();
    responseDTO.setId(recruitment.getId());
    responseDTO.setName(recruitment.getName());
    responseDTO.setDescription(recruitment.getDescription());
    responseDTO.setBody(recruitment.getBody());
    responseDTO.setCompanyName(recruitment.getCompanyName());
    responseDTO.setPosition(recruitment.getPosition());
    responseDTO.setLink(recruitment.getLink());
    responseDTO.setRecruitmentStatus(recruitment.getRecruitmentStatus());
    responseDTO.setWorkType(recruitment.getWorkType());
    return responseDTO;
  }

}
