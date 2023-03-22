package com.jobis.domain.entity;

import com.jobis.domain.code.RecruitmentStatus;
import com.jobis.domain.code.WorkType;
import java.util.Objects;
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

@Setter
@Getter
@Entity
public class Recruitment extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recruitment_id")
  private Long id;
  private String name;
  private String description;
  @Column(columnDefinition = "TEXT")
  private String body;
  private String companyName;
  private String position;
  private String link;
  private String color;
  @Enumerated(EnumType.STRING)
  @Column(name = "recruitment_status", nullable = false)
  private RecruitmentStatus recruitmentStatus;
  @Enumerated(EnumType.STRING)
  @Column(name = "work_type")
  private WorkType workType;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Recruitment)) {
      return false;
    }
    Recruitment that = (Recruitment) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
