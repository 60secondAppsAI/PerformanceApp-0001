package com.PerformanceApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.PerformanceApp.dao.GenericDAO;
import com.PerformanceApp.service.GenericService;
import com.PerformanceApp.service.impl.GenericServiceImpl;
import com.PerformanceApp.dao.PerformanceReportDAO;
import com.PerformanceApp.domain.PerformanceReport;
import com.PerformanceApp.dto.PerformanceReportDTO;
import com.PerformanceApp.dto.PerformanceReportSearchDTO;
import com.PerformanceApp.dto.PerformanceReportPageDTO;
import com.PerformanceApp.dto.PerformanceReportConvertCriteriaDTO;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import com.PerformanceApp.service.PerformanceReportService;
import com.PerformanceApp.util.ControllerUtils;


@Service
public class PerformanceReportServiceImpl extends GenericServiceImpl<PerformanceReport, Integer> implements PerformanceReportService {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceReportServiceImpl.class);

	@Autowired
	PerformanceReportDAO performanceReportDao;

	

	@Override
	public GenericDAO<PerformanceReport, Integer> getDAO() {
		return (GenericDAO<PerformanceReport, Integer>) performanceReportDao;
	}
	
	public List<PerformanceReport> findAll () {
		List<PerformanceReport> performanceReports = performanceReportDao.findAll();
		
		return performanceReports;	
		
	}

	public ResultDTO addPerformanceReport(PerformanceReportDTO performanceReportDTO, RequestDTO requestDTO) {

		PerformanceReport performanceReport = new PerformanceReport();

		performanceReport.setPerformanceReportId(performanceReportDTO.getPerformanceReportId());

		performanceReport.setPortfolioId(performanceReportDTO.getPortfolioId());

		performanceReport.setStartDate(performanceReportDTO.getStartDate());

		performanceReport.setEndDate(performanceReportDTO.getEndDate());

		performanceReport.setTotalReturn(performanceReportDTO.getTotalReturn());

		performanceReport.setBenchmarkReturn(performanceReportDTO.getBenchmarkReturn());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		performanceReport = performanceReportDao.save(performanceReport);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PerformanceReport> getAllPerformanceReports(Pageable pageable) {
		return performanceReportDao.findAll(pageable);
	}

	public Page<PerformanceReport> getAllPerformanceReports(Specification<PerformanceReport> spec, Pageable pageable) {
		return performanceReportDao.findAll(spec, pageable);
	}

	public ResponseEntity<PerformanceReportPageDTO> getPerformanceReports(PerformanceReportSearchDTO performanceReportSearchDTO) {
	
			Integer performanceReportId = performanceReportSearchDTO.getPerformanceReportId(); 
 			Integer portfolioId = performanceReportSearchDTO.getPortfolioId(); 
 			Date startDate = performanceReportSearchDTO.getStartDate();
 			Date endDate = performanceReportSearchDTO.getEndDate();
   			String sortBy = performanceReportSearchDTO.getSortBy();
			String sortOrder = performanceReportSearchDTO.getSortOrder();
			String searchQuery = performanceReportSearchDTO.getSearchQuery();
			Integer page = performanceReportSearchDTO.getPage();
			Integer size = performanceReportSearchDTO.getSize();

	        Specification<PerformanceReport> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, performanceReportId, "performanceReportId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, startDate, "startDate");
			
			spec = ControllerUtils.andIfNecessary(spec, endDate, "endDate");
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<PerformanceReport> performanceReports = this.getAllPerformanceReports(spec, pageable);
		
		//System.out.println(String.valueOf(performanceReports.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(performanceReports.getTotalPages()));
		
		List<PerformanceReport> performanceReportsList = performanceReports.getContent();
		
		PerformanceReportConvertCriteriaDTO convertCriteria = new PerformanceReportConvertCriteriaDTO();
		List<PerformanceReportDTO> performanceReportDTOs = this.convertPerformanceReportsToPerformanceReportDTOs(performanceReportsList,convertCriteria);
		
		PerformanceReportPageDTO performanceReportPageDTO = new PerformanceReportPageDTO();
		performanceReportPageDTO.setPerformanceReports(performanceReportDTOs);
		performanceReportPageDTO.setTotalElements(performanceReports.getTotalElements());
		return ResponseEntity.ok(performanceReportPageDTO);
	}

	public List<PerformanceReportDTO> convertPerformanceReportsToPerformanceReportDTOs(List<PerformanceReport> performanceReports, PerformanceReportConvertCriteriaDTO convertCriteria) {
		
		List<PerformanceReportDTO> performanceReportDTOs = new ArrayList<PerformanceReportDTO>();
		
		for (PerformanceReport performanceReport : performanceReports) {
			performanceReportDTOs.add(convertPerformanceReportToPerformanceReportDTO(performanceReport,convertCriteria));
		}
		
		return performanceReportDTOs;

	}
	
	public PerformanceReportDTO convertPerformanceReportToPerformanceReportDTO(PerformanceReport performanceReport, PerformanceReportConvertCriteriaDTO convertCriteria) {
		
		PerformanceReportDTO performanceReportDTO = new PerformanceReportDTO();

		performanceReportDTO.setPerformanceReportId(performanceReport.getPerformanceReportId());

		performanceReportDTO.setPortfolioId(performanceReport.getPortfolioId());

		performanceReportDTO.setStartDate(performanceReport.getStartDate());

		performanceReportDTO.setEndDate(performanceReport.getEndDate());

		performanceReportDTO.setTotalReturn(performanceReport.getTotalReturn());

		performanceReportDTO.setBenchmarkReturn(performanceReport.getBenchmarkReturn());
		
		return performanceReportDTO;
	}

	public ResultDTO updatePerformanceReport(PerformanceReportDTO performanceReportDTO, RequestDTO requestDTO) {
		
		PerformanceReport performanceReport = performanceReportDao.getById(performanceReportDTO.getPerformanceReportId());
		
		performanceReport.setPerformanceReportId(ControllerUtils.setValue(performanceReport.getPerformanceReportId(), performanceReportDTO.getPerformanceReportId()));
		
		performanceReport.setPortfolioId(ControllerUtils.setValue(performanceReport.getPortfolioId(), performanceReportDTO.getPortfolioId()));
		
		performanceReport.setStartDate(ControllerUtils.setValue(performanceReport.getStartDate(), performanceReportDTO.getStartDate()));
		
		performanceReport.setEndDate(ControllerUtils.setValue(performanceReport.getEndDate(), performanceReportDTO.getEndDate()));
		
		performanceReport.setTotalReturn(ControllerUtils.setValue(performanceReport.getTotalReturn(), performanceReportDTO.getTotalReturn()));
		
		performanceReport.setBenchmarkReturn(ControllerUtils.setValue(performanceReport.getBenchmarkReturn(), performanceReportDTO.getBenchmarkReturn()));

        performanceReport = performanceReportDao.save(performanceReport);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PerformanceReportDTO getPerformanceReportDTOById(Integer performanceReportId) {
	
		PerformanceReport performanceReport = performanceReportDao.getById(performanceReportId);
		
		PerformanceReportConvertCriteriaDTO convertCriteria = new PerformanceReportConvertCriteriaDTO();
		return(this.convertPerformanceReportToPerformanceReportDTO(performanceReport,convertCriteria));
	}

}
