package com.PerformanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PerformanceApp.domain.PerformanceReport;
import com.PerformanceApp.dto.PerformanceReportDTO;
import com.PerformanceApp.dto.PerformanceReportSearchDTO;
import com.PerformanceApp.dto.PerformanceReportPageDTO;
import com.PerformanceApp.dto.PerformanceReportConvertCriteriaDTO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PerformanceReportService extends GenericService<PerformanceReport, Integer> {

	List<PerformanceReport> findAll();

	ResultDTO addPerformanceReport(PerformanceReportDTO performanceReportDTO, RequestDTO requestDTO);

	ResultDTO updatePerformanceReport(PerformanceReportDTO performanceReportDTO, RequestDTO requestDTO);

    Page<PerformanceReport> getAllPerformanceReports(Pageable pageable);

    Page<PerformanceReport> getAllPerformanceReports(Specification<PerformanceReport> spec, Pageable pageable);

	ResponseEntity<PerformanceReportPageDTO> getPerformanceReports(PerformanceReportSearchDTO performanceReportSearchDTO);
	
	List<PerformanceReportDTO> convertPerformanceReportsToPerformanceReportDTOs(List<PerformanceReport> performanceReports, PerformanceReportConvertCriteriaDTO convertCriteria);

	PerformanceReportDTO getPerformanceReportDTOById(Integer performanceReportId);



	
}
