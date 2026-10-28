package com.PerformanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PerformanceApp.domain.PerformancePeriod;
import com.PerformanceApp.dto.PerformancePeriodDTO;
import com.PerformanceApp.dto.PerformancePeriodSearchDTO;
import com.PerformanceApp.dto.PerformancePeriodPageDTO;
import com.PerformanceApp.dto.PerformancePeriodConvertCriteriaDTO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PerformancePeriodService extends GenericService<PerformancePeriod, Integer> {

	List<PerformancePeriod> findAll();

	ResultDTO addPerformancePeriod(PerformancePeriodDTO performancePeriodDTO, RequestDTO requestDTO);

	ResultDTO updatePerformancePeriod(PerformancePeriodDTO performancePeriodDTO, RequestDTO requestDTO);

    Page<PerformancePeriod> getAllPerformancePeriods(Pageable pageable);

    Page<PerformancePeriod> getAllPerformancePeriods(Specification<PerformancePeriod> spec, Pageable pageable);

	ResponseEntity<PerformancePeriodPageDTO> getPerformancePeriods(PerformancePeriodSearchDTO performancePeriodSearchDTO);
	
	List<PerformancePeriodDTO> convertPerformancePeriodsToPerformancePeriodDTOs(List<PerformancePeriod> performancePeriods, PerformancePeriodConvertCriteriaDTO convertCriteria);

	PerformancePeriodDTO getPerformancePeriodDTOById(Integer performancePeriodId);



	
}
