package com.jobis.domain.repository.holiday;

import com.jobis.domain.repository.holiday.param.SearchHolidayParam;
import com.jobis.web.dto.response.HolidayResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HolidayRepositoryCustom {

  Page<HolidayResponseDTO> findAll(SearchHolidayParam param, Pageable pageable);

}
