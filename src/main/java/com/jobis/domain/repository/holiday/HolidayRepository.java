package com.jobis.domain.repository.holiday;

import com.jobis.domain.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>,
    HolidayRepositoryCustom {

}
