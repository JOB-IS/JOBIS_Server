package com.jobis.domain.repository.recruitmentSchedule;

import com.jobis.domain.entity.RecruitmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentScheduleRepository extends JpaRepository<RecruitmentSchedule, Long>,
    RecruitmentScheduleRepositoryCustom {

}
