package com.jobis.domain.repository.recruitment;

import com.jobis.domain.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>,
    RecruitmentRepositoryCustom {

}
