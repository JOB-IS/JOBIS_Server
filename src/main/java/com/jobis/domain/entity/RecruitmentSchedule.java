package com.jobis.domain.entity;

import com.jobis.domain.code.RecruitmentScheduleStatus;
import com.jobis.domain.code.RecruitmentScheduleType;
import java.time.LocalDateTime;
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
public class RecruitmentSchedule extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "recruitment_schedule_id")
  private Long id;
  private String name;
  private String description;
  @Column(columnDefinition = "TEXT")
  private String body;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  @Column(name = "recruitment_schedule_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private RecruitmentScheduleType recruitmentScheduleType;
  @Column(name = "recruitment_schedule_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private RecruitmentScheduleStatus recruitmentScheduleStatus;

  @ManyToOne
  @JoinColumn(name = "recruitment_id", referencedColumnName = "recruitment_id")
  private Recruitment recruitment;

}
