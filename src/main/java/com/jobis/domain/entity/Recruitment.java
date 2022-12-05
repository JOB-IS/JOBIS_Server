package com.jobis.domain.entity;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recruitment extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "recruitment_id")
  private Long id;
  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;
  @Column(columnDefinition = "TEXT")
  private String body;
  private String position;
  private String link;
  @Enumerated(EnumType.STRING)
  @Column(name = "recruitment_status", nullable = false)
  private RecruitmentStatus recruitmentStatus;
  @Enumerated(EnumType.STRING)
  @Column(name = "work_type")
  private WorkType workType;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  private User user;

}
