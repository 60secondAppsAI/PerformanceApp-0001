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
import com.PerformanceApp.dao.PerformanceMetricDAO;
import com.PerformanceApp.domain.PerformanceMetric;
import com.PerformanceApp.dto.PerformanceMetricDTO;
import com.PerformanceApp.dto.PerformanceMetricSearchDTO;
import com.PerformanceApp.dto.PerformanceMetricPageDTO;
import com.PerformanceApp.dto.PerformanceMetricConvertCriteriaDTO;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import com.PerformanceApp.service.PerformanceMetricService;
import com.PerformanceApp.util.ControllerUtils;


@Service
public class PerformanceMetricServiceImpl extends GenericServiceImpl<PerformanceMetric, Integer> implements PerformanceMetricService {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceMetricServiceImpl.class);

	@Autowired
	PerformanceMetricDAO performanceMetricDao;

	

	@Override
	public GenericDAO<PerformanceMetric, Integer> getDAO() {
		return (GenericDAO<PerformanceMetric, Integer>) performanceMetricDao;
	}
	
	public List<PerformanceMetric> findAll () {
		List<PerformanceMetric> performanceMetrics = performanceMetricDao.findAll();
		
		return performanceMetrics;	
		
	}

	public ResultDTO addPerformanceMetric(PerformanceMetricDTO performanceMetricDTO, RequestDTO requestDTO) {

		PerformanceMetric performanceMetric = new PerformanceMetric();

		performanceMetric.setPerformanceMetricId(performanceMetricDTO.getPerformanceMetricId());

		performanceMetric.setPerformanceReportId(performanceMetricDTO.getPerformanceReportId());

		performanceMetric.setMetricName(performanceMetricDTO.getMetricName());

		performanceMetric.setMetricValue(performanceMetricDTO.getMetricValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		performanceMetric = performanceMetricDao.save(performanceMetric);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PerformanceMetric> getAllPerformanceMetrics(Pageable pageable) {
		return performanceMetricDao.findAll(pageable);
	}

	public Page<PerformanceMetric> getAllPerformanceMetrics(Specification<PerformanceMetric> spec, Pageable pageable) {
		return performanceMetricDao.findAll(spec, pageable);
	}

	public ResponseEntity<PerformanceMetricPageDTO> getPerformanceMetrics(PerformanceMetricSearchDTO performanceMetricSearchDTO) {
	
			Integer performanceMetricId = performanceMetricSearchDTO.getPerformanceMetricId(); 
 			Integer performanceReportId = performanceMetricSearchDTO.getPerformanceReportId(); 
 			String metricName = performanceMetricSearchDTO.getMetricName(); 
  			String sortBy = performanceMetricSearchDTO.getSortBy();
			String sortOrder = performanceMetricSearchDTO.getSortOrder();
			String searchQuery = performanceMetricSearchDTO.getSearchQuery();
			Integer page = performanceMetricSearchDTO.getPage();
			Integer size = performanceMetricSearchDTO.getSize();

	        Specification<PerformanceMetric> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, performanceMetricId, "performanceMetricId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, performanceReportId, "performanceReportId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, metricName, "metricName"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("metricName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PerformanceMetric> performanceMetrics = this.getAllPerformanceMetrics(spec, pageable);
		
		//System.out.println(String.valueOf(performanceMetrics.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(performanceMetrics.getTotalPages()));
		
		List<PerformanceMetric> performanceMetricsList = performanceMetrics.getContent();
		
		PerformanceMetricConvertCriteriaDTO convertCriteria = new PerformanceMetricConvertCriteriaDTO();
		List<PerformanceMetricDTO> performanceMetricDTOs = this.convertPerformanceMetricsToPerformanceMetricDTOs(performanceMetricsList,convertCriteria);
		
		PerformanceMetricPageDTO performanceMetricPageDTO = new PerformanceMetricPageDTO();
		performanceMetricPageDTO.setPerformanceMetrics(performanceMetricDTOs);
		performanceMetricPageDTO.setTotalElements(performanceMetrics.getTotalElements());
		return ResponseEntity.ok(performanceMetricPageDTO);
	}

	public List<PerformanceMetricDTO> convertPerformanceMetricsToPerformanceMetricDTOs(List<PerformanceMetric> performanceMetrics, PerformanceMetricConvertCriteriaDTO convertCriteria) {
		
		List<PerformanceMetricDTO> performanceMetricDTOs = new ArrayList<PerformanceMetricDTO>();
		
		for (PerformanceMetric performanceMetric : performanceMetrics) {
			performanceMetricDTOs.add(convertPerformanceMetricToPerformanceMetricDTO(performanceMetric,convertCriteria));
		}
		
		return performanceMetricDTOs;

	}
	
	public PerformanceMetricDTO convertPerformanceMetricToPerformanceMetricDTO(PerformanceMetric performanceMetric, PerformanceMetricConvertCriteriaDTO convertCriteria) {
		
		PerformanceMetricDTO performanceMetricDTO = new PerformanceMetricDTO();

		performanceMetricDTO.setPerformanceMetricId(performanceMetric.getPerformanceMetricId());

		performanceMetricDTO.setPerformanceReportId(performanceMetric.getPerformanceReportId());

		performanceMetricDTO.setMetricName(performanceMetric.getMetricName());

		performanceMetricDTO.setMetricValue(performanceMetric.getMetricValue());
		
		return performanceMetricDTO;
	}

	public ResultDTO updatePerformanceMetric(PerformanceMetricDTO performanceMetricDTO, RequestDTO requestDTO) {
		
		PerformanceMetric performanceMetric = performanceMetricDao.getById(performanceMetricDTO.getPerformanceMetricId());
		
		performanceMetric.setPerformanceMetricId(ControllerUtils.setValue(performanceMetric.getPerformanceMetricId(), performanceMetricDTO.getPerformanceMetricId()));
		
		performanceMetric.setPerformanceReportId(ControllerUtils.setValue(performanceMetric.getPerformanceReportId(), performanceMetricDTO.getPerformanceReportId()));
		
		performanceMetric.setMetricName(ControllerUtils.setValue(performanceMetric.getMetricName(), performanceMetricDTO.getMetricName()));
		
		performanceMetric.setMetricValue(ControllerUtils.setValue(performanceMetric.getMetricValue(), performanceMetricDTO.getMetricValue()));

        performanceMetric = performanceMetricDao.save(performanceMetric);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PerformanceMetricDTO getPerformanceMetricDTOById(Integer performanceMetricId) {
	
		PerformanceMetric performanceMetric = performanceMetricDao.getById(performanceMetricId);
		
		PerformanceMetricConvertCriteriaDTO convertCriteria = new PerformanceMetricConvertCriteriaDTO();
		return(this.convertPerformanceMetricToPerformanceMetricDTO(performanceMetric,convertCriteria));
	}

}
