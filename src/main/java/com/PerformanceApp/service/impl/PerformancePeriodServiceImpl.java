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
import com.PerformanceApp.dao.PerformancePeriodDAO;
import com.PerformanceApp.domain.PerformancePeriod;
import com.PerformanceApp.dto.PerformancePeriodDTO;
import com.PerformanceApp.dto.PerformancePeriodSearchDTO;
import com.PerformanceApp.dto.PerformancePeriodPageDTO;
import com.PerformanceApp.dto.PerformancePeriodConvertCriteriaDTO;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import com.PerformanceApp.service.PerformancePeriodService;
import com.PerformanceApp.util.ControllerUtils;


@Service
public class PerformancePeriodServiceImpl extends GenericServiceImpl<PerformancePeriod, Integer> implements PerformancePeriodService {

    private final static Logger logger = LoggerFactory.getLogger(PerformancePeriodServiceImpl.class);

	@Autowired
	PerformancePeriodDAO performancePeriodDao;

	

	@Override
	public GenericDAO<PerformancePeriod, Integer> getDAO() {
		return (GenericDAO<PerformancePeriod, Integer>) performancePeriodDao;
	}
	
	public List<PerformancePeriod> findAll () {
		List<PerformancePeriod> performancePeriods = performancePeriodDao.findAll();
		
		return performancePeriods;	
		
	}

	public ResultDTO addPerformancePeriod(PerformancePeriodDTO performancePeriodDTO, RequestDTO requestDTO) {

		PerformancePeriod performancePeriod = new PerformancePeriod();

		performancePeriod.setPerformancePeriodId(performancePeriodDTO.getPerformancePeriodId());

		performancePeriod.setPerformanceReportId(performancePeriodDTO.getPerformanceReportId());

		performancePeriod.setPeriodDate(performancePeriodDTO.getPeriodDate());

		performancePeriod.setPortfolioReturn(performancePeriodDTO.getPortfolioReturn());

		performancePeriod.setBenchmarkReturn(performancePeriodDTO.getBenchmarkReturn());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		performancePeriod = performancePeriodDao.save(performancePeriod);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PerformancePeriod> getAllPerformancePeriods(Pageable pageable) {
		return performancePeriodDao.findAll(pageable);
	}

	public Page<PerformancePeriod> getAllPerformancePeriods(Specification<PerformancePeriod> spec, Pageable pageable) {
		return performancePeriodDao.findAll(spec, pageable);
	}

	public ResponseEntity<PerformancePeriodPageDTO> getPerformancePeriods(PerformancePeriodSearchDTO performancePeriodSearchDTO) {
	
			Integer performancePeriodId = performancePeriodSearchDTO.getPerformancePeriodId(); 
 			Integer performanceReportId = performancePeriodSearchDTO.getPerformanceReportId(); 
 			Date periodDate = performancePeriodSearchDTO.getPeriodDate();
   			String sortBy = performancePeriodSearchDTO.getSortBy();
			String sortOrder = performancePeriodSearchDTO.getSortOrder();
			String searchQuery = performancePeriodSearchDTO.getSearchQuery();
			Integer page = performancePeriodSearchDTO.getPage();
			Integer size = performancePeriodSearchDTO.getSize();

	        Specification<PerformancePeriod> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, performancePeriodId, "performancePeriodId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, performanceReportId, "performanceReportId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, periodDate, "periodDate");
			
			
			

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

		Page<PerformancePeriod> performancePeriods = this.getAllPerformancePeriods(spec, pageable);
		
		//System.out.println(String.valueOf(performancePeriods.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(performancePeriods.getTotalPages()));
		
		List<PerformancePeriod> performancePeriodsList = performancePeriods.getContent();
		
		PerformancePeriodConvertCriteriaDTO convertCriteria = new PerformancePeriodConvertCriteriaDTO();
		List<PerformancePeriodDTO> performancePeriodDTOs = this.convertPerformancePeriodsToPerformancePeriodDTOs(performancePeriodsList,convertCriteria);
		
		PerformancePeriodPageDTO performancePeriodPageDTO = new PerformancePeriodPageDTO();
		performancePeriodPageDTO.setPerformancePeriods(performancePeriodDTOs);
		performancePeriodPageDTO.setTotalElements(performancePeriods.getTotalElements());
		return ResponseEntity.ok(performancePeriodPageDTO);
	}

	public List<PerformancePeriodDTO> convertPerformancePeriodsToPerformancePeriodDTOs(List<PerformancePeriod> performancePeriods, PerformancePeriodConvertCriteriaDTO convertCriteria) {
		
		List<PerformancePeriodDTO> performancePeriodDTOs = new ArrayList<PerformancePeriodDTO>();
		
		for (PerformancePeriod performancePeriod : performancePeriods) {
			performancePeriodDTOs.add(convertPerformancePeriodToPerformancePeriodDTO(performancePeriod,convertCriteria));
		}
		
		return performancePeriodDTOs;

	}
	
	public PerformancePeriodDTO convertPerformancePeriodToPerformancePeriodDTO(PerformancePeriod performancePeriod, PerformancePeriodConvertCriteriaDTO convertCriteria) {
		
		PerformancePeriodDTO performancePeriodDTO = new PerformancePeriodDTO();

		performancePeriodDTO.setPerformancePeriodId(performancePeriod.getPerformancePeriodId());

		performancePeriodDTO.setPerformanceReportId(performancePeriod.getPerformanceReportId());

		performancePeriodDTO.setPeriodDate(performancePeriod.getPeriodDate());

		performancePeriodDTO.setPortfolioReturn(performancePeriod.getPortfolioReturn());

		performancePeriodDTO.setBenchmarkReturn(performancePeriod.getBenchmarkReturn());
		
		return performancePeriodDTO;
	}

	public ResultDTO updatePerformancePeriod(PerformancePeriodDTO performancePeriodDTO, RequestDTO requestDTO) {
		
		PerformancePeriod performancePeriod = performancePeriodDao.getById(performancePeriodDTO.getPerformancePeriodId());
		
		performancePeriod.setPerformancePeriodId(ControllerUtils.setValue(performancePeriod.getPerformancePeriodId(), performancePeriodDTO.getPerformancePeriodId()));
		
		performancePeriod.setPerformanceReportId(ControllerUtils.setValue(performancePeriod.getPerformanceReportId(), performancePeriodDTO.getPerformanceReportId()));
		
		performancePeriod.setPeriodDate(ControllerUtils.setValue(performancePeriod.getPeriodDate(), performancePeriodDTO.getPeriodDate()));
		
		performancePeriod.setPortfolioReturn(ControllerUtils.setValue(performancePeriod.getPortfolioReturn(), performancePeriodDTO.getPortfolioReturn()));
		
		performancePeriod.setBenchmarkReturn(ControllerUtils.setValue(performancePeriod.getBenchmarkReturn(), performancePeriodDTO.getBenchmarkReturn()));

        performancePeriod = performancePeriodDao.save(performancePeriod);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PerformancePeriodDTO getPerformancePeriodDTOById(Integer performancePeriodId) {
	
		PerformancePeriod performancePeriod = performancePeriodDao.getById(performancePeriodId);
		
		PerformancePeriodConvertCriteriaDTO convertCriteria = new PerformancePeriodConvertCriteriaDTO();
		return(this.convertPerformancePeriodToPerformancePeriodDTO(performancePeriod,convertCriteria));
	}

}
