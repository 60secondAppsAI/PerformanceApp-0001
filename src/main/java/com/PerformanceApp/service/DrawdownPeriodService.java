package com.PerformanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PerformanceApp.domain.DrawdownPeriod;
import com.PerformanceApp.dto.DrawdownPeriodDTO;
import com.PerformanceApp.dto.DrawdownPeriodSearchDTO;
import com.PerformanceApp.dto.DrawdownPeriodPageDTO;
import com.PerformanceApp.dto.DrawdownPeriodConvertCriteriaDTO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DrawdownPeriodService extends GenericService<DrawdownPeriod, Integer> {

	List<DrawdownPeriod> findAll();

	ResultDTO addDrawdownPeriod(DrawdownPeriodDTO drawdownPeriodDTO, RequestDTO requestDTO);

	ResultDTO updateDrawdownPeriod(DrawdownPeriodDTO drawdownPeriodDTO, RequestDTO requestDTO);

    Page<DrawdownPeriod> getAllDrawdownPeriods(Pageable pageable);

    Page<DrawdownPeriod> getAllDrawdownPeriods(Specification<DrawdownPeriod> spec, Pageable pageable);

	ResponseEntity<DrawdownPeriodPageDTO> getDrawdownPeriods(DrawdownPeriodSearchDTO drawdownPeriodSearchDTO);
	
	List<DrawdownPeriodDTO> convertDrawdownPeriodsToDrawdownPeriodDTOs(List<DrawdownPeriod> drawdownPeriods, DrawdownPeriodConvertCriteriaDTO convertCriteria);

	DrawdownPeriodDTO getDrawdownPeriodDTOById(Integer drawdownPeriodId);



	
}
