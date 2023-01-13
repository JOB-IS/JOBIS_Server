package com.jobis.domain.repository.holiday;

import com.jobis.domain.entity.Holiday;
import com.jobis.domain.entity.QHoliday;
import com.jobis.domain.repository.holiday.param.SearchHolidayParam;
import com.jobis.web.dto.response.HolidayResponseDTO;
import com.jobis.web.dto.response.QHolidayResponseDTO;
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

public class HolidayRepositoryImpl extends QuerydslRepositorySupport implements
    HolidayRepositoryCustom {

  private final JPAQueryFactory query;
  private QHoliday holiday;

  public HolidayRepositoryImpl(EntityManager em) {
    super(Holiday.class);
    query = new JPAQueryFactory(em);
    holiday = new QHoliday("holiday");
  }

  @Override
  public Page<HolidayResponseDTO> findAll(SearchHolidayParam param, Pageable pageable) {

    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<HolidayResponseDTO> jpaQuery = query.select(
        new QHolidayResponseDTO(
            holiday.id,
            holiday.dateName,
            holiday.holiday,
            holiday.date,
            holiday.holidayType
        ))
        .from(holiday)
        .where(whereCondition)
        .orderBy(holiday.date.asc());

    long totalCount = jpaQuery.fetchCount();
    List<HolidayResponseDTO> results = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
    return new PageImpl<>(results, pageable, totalCount);
  }

  public BooleanBuilder getWhereCondition(SearchHolidayParam param) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (Objects.nonNull(param.getHoliday())) {
      booleanBuilder.and(holiday.holiday.eq(param.getHoliday()));
    }
    if (param.getFromDate() != null) {
      booleanBuilder.and(holiday.date.goe(param.getFromDate())
          .or(holiday.date.goe(param.getFromDate())));
    }
    if (param.getToDate() != null) {
      booleanBuilder.and(holiday.date.lt(param.getToDate().plusDays(1))
          .or(holiday.date.loe(param.getToDate())));
    }
    return booleanBuilder;
  }
}
