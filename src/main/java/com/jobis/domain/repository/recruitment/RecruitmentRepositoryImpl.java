package com.jobis.domain.repository.recruitment;

import static com.jobis.domain.entity.QRecruitment.recruitment;


import com.jobis.domain.entity.QUser;
import com.jobis.domain.entity.Recruitment;
import com.jobis.domain.repository.recruitment.param.SearchRecruitmentParam;
import com.jobis.web.dto.response.QRecruitmentResponseDTO;
import com.jobis.web.dto.response.RecruitmentResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RecruitmentRepositoryImpl extends QuerydslRepositorySupport implements
    RecruitmentRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private QUser user;

  public RecruitmentRepositoryImpl(EntityManager em) {
    super(Recruitment.class);
    queryFactory = new JPAQueryFactory(em);
    user = new QUser("user");
  }

  @Override
  public Page<RecruitmentResponseDTO> findAll(SearchRecruitmentParam param, Pageable pageable) {

    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<RecruitmentResponseDTO> jpaQuery = queryFactory.select(
        new QRecruitmentResponseDTO(
            recruitment.id,
            recruitment.name,
            recruitment.description,
            recruitment.body,
            recruitment.companyName,
            recruitment.position,
            recruitment.link,
            recruitment.color,
            recruitment.recruitmentStatus,
            recruitment.workType
        ))
        .from(recruitment)
        .leftJoin(recruitment.user, user)
        .where(whereCondition)
        .orderBy(recruitment.id.desc());

    long totalCount = jpaQuery.fetchCount();
    List<RecruitmentResponseDTO> results = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
    return new PageImpl<>(results, pageable, totalCount);
  }

  public BooleanBuilder getWhereCondition(SearchRecruitmentParam param) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Objects.nonNull(param.getUserId())) {
      booleanBuilder.and(recruitment.user.id.eq(param.getUserId()));
    }
    if (Objects.nonNull(param.getRecruitmentStatus())) {
      booleanBuilder.and(recruitment.recruitmentStatus.eq(param.getRecruitmentStatus()));
    }
    if (Objects.nonNull(param.getWorkType())) {
      booleanBuilder.and(recruitment.workType.eq(param.getWorkType()));
    }
    return booleanBuilder;
  }

}
