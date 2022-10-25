package com.catchup.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public class BaseTime {

  @CreationTimestamp
  private LocalDateTime createdDateTime;
  @UpdateTimestamp
  private LocalDateTime updatedDateTime;

}
