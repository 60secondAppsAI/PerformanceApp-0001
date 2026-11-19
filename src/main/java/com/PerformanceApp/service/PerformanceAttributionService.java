package com.PerformanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PerformanceApp.domain.PerformanceAttribution;
import com.PerformanceApp.dto.PerformanceAttributionDTO;
import com.PerformanceApp.dto.PerformanceAttributionSearchDTO;
import com.PerformanceApp.dto.PerformanceAttributionPageDTO;
import com.PerformanceApp.dto.PerformanceAttributionConvertCriteriaDTO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PerformanceAttributionService extends GenericService<PerformanceAttribution, Integer> {

	List<PerformanceAttribution> findAll();

	ResultDTO addPerformanceAttribution(PerformanceAttributionDTO performanceAttributionDTO, RequestDTO requestDTO);

	ResultDTO updatePerformanceAttribution(PerformanceAttributionDTO performanceAttributionDTO, RequestDTO requestDTO);

    Page<PerformanceAttribution> getAllPerformanceAttributions(Pageable pageable);

    Page<PerformanceAttribution> getAllPerformanceAttributions(Specification<PerformanceAttribution> spec, Pageable pageable);

	ResponseEntity<PerformanceAttributionPageDTO> getPerformanceAttributions(PerformanceAttributionSearchDTO performanceAttributionSearchDTO);
	
	List<PerformanceAttributionDTO> convertPerformanceAttributionsToPerformanceAttributionDTOs(List<PerformanceAttribution> performanceAttributions, PerformanceAttributionConvertCriteriaDTO convertCriteria);

	PerformanceAttributionDTO getPerformanceAttributionDTOById(Integer performanceAttributionId);



	
}
