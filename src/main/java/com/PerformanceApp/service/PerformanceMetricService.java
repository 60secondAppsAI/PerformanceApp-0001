package com.PerformanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PerformanceApp.domain.PerformanceMetric;
import com.PerformanceApp.dto.PerformanceMetricDTO;
import com.PerformanceApp.dto.PerformanceMetricSearchDTO;
import com.PerformanceApp.dto.PerformanceMetricPageDTO;
import com.PerformanceApp.dto.PerformanceMetricConvertCriteriaDTO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PerformanceMetricService extends GenericService<PerformanceMetric, Integer> {

	List<PerformanceMetric> findAll();

	ResultDTO addPerformanceMetric(PerformanceMetricDTO performanceMetricDTO, RequestDTO requestDTO);

	ResultDTO updatePerformanceMetric(PerformanceMetricDTO performanceMetricDTO, RequestDTO requestDTO);

    Page<PerformanceMetric> getAllPerformanceMetrics(Pageable pageable);

    Page<PerformanceMetric> getAllPerformanceMetrics(Specification<PerformanceMetric> spec, Pageable pageable);

	ResponseEntity<PerformanceMetricPageDTO> getPerformanceMetrics(PerformanceMetricSearchDTO performanceMetricSearchDTO);
	
	List<PerformanceMetricDTO> convertPerformanceMetricsToPerformanceMetricDTOs(List<PerformanceMetric> performanceMetrics, PerformanceMetricConvertCriteriaDTO convertCriteria);

	PerformanceMetricDTO getPerformanceMetricDTOById(Integer performanceMetricId);



	
}
