package com.jobis.domain.repository.recruitmentSchedule;

import static com.jobis.domain.entity.QRecruitmentSchedule.recruitmentSchedule;

import com.jobis.domain.entity.QRecruitment;
import com.jobis.domain.entity.QUser;
import com.jobis.domain.entity.RecruitmentSchedule;
import com.jobis.domain.repository.recruitmentSchedule.param.SearchRecruitmentScheduleParam;
import com.jobis.util.DateUtil;
import com.jobis.web.dto.response.QRecruitmentScheduleResponseDTO;
import com.jobis.web.dto.response.RecruitmentScheduleResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RecruitmentScheduleRepositoryImpl extends QuerydslRepositorySupport implements
    RecruitmentScheduleRepositoryCustom {

  private final JPAQueryFactory query;
  private QRecruitment recruitment;
  private QUser user;

  public RecruitmentScheduleRepositoryImpl(EntityManager em) {
    super(RecruitmentSchedule.class);
    query = new JPAQueryFactory(em);
    recruitment = new QRecruitment("recruitment");
    user = new QUser("user");
  }

  @Override
  public Page<RecruitmentScheduleResponseDTO> findAll(SearchRecruitmentScheduleParam param,
      Pageable pageable) {

    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<RecruitmentScheduleResponseDTO> jpaQuery = query.select(
        new QRecruitmentScheduleResponseDTO(
            recruitmentSchedule.id,
            recruitmentSchedule.name,
            recruitmentSchedule.description,
            recruitmentSchedule.body,
            recruitmentSchedule.startDateTime,
            recruitmentSchedule.endDateTime,
            recruitmentSchedule.startDate,
            recruitmentSchedule.endDate,
            recruitmentSchedule.recruitmentScheduleType,
            recruitmentSchedule.recruitmentScheduleStatus
        ))
        .from(recruitmentSchedule)
        .leftJoin(recruitmentSchedule.recruitment, recruitment)
        .leftJoin(recruitment.user, user)
        .where(whereCondition)
        .orderBy(recruitmentSchedule.id.desc());

    long totalCount = jpaQuery.fetchCount();
    List<RecruitmentScheduleResponseDTO> results = getQuerydsl().applyPagination(pageable, jpaQuery)
        .fetch();
    return new PageImpl<>(results, pageable, totalCount);
  }

  public BooleanBuilder getWhereCondition(SearchRecruitmentScheduleParam param) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (param.getFromDate() != null) {
      booleanBuilder.and(recruitmentSchedule.endDateTime.goe(
          DateUtil.convertLocalDateToLocalDateTime(param.getFromDate()))
          .or(recruitmentSchedule.endDate.goe(param.getFromDate())));
    }
    if (param.getToDate() != null) {
      booleanBuilder.and(recruitmentSchedule.startDateTime.lt(
          DateUtil.convertLocalDateToLocalDateTime(param.getToDate().plusDays(1)))
          .or(recruitmentSchedule.startDate.loe(param.getToDate())));
    }
    if (param.getRecruitmentScheduleType() != null) {
      booleanBuilder.and(
          recruitmentSchedule.recruitmentScheduleType.eq(param.getRecruitmentScheduleType()));
    }
    if (param.getRecruitmentScheduleStatus() != null) {
      booleanBuilder.and(
          recruitmentSchedule.recruitmentScheduleStatus.eq(param.getRecruitmentScheduleStatus()));
    }
    if (param.getUserId() != null) {
      booleanBuilder.and(user.id.eq(param.getUserId()));
    }
    return booleanBuilder;
  }
}
