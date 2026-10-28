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
import com.PerformanceApp.dao.PerformanceAttributionDAO;
import com.PerformanceApp.domain.PerformanceAttribution;
import com.PerformanceApp.dto.PerformanceAttributionDTO;
import com.PerformanceApp.dto.PerformanceAttributionSearchDTO;
import com.PerformanceApp.dto.PerformanceAttributionPageDTO;
import com.PerformanceApp.dto.PerformanceAttributionConvertCriteriaDTO;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import com.PerformanceApp.service.PerformanceAttributionService;
import com.PerformanceApp.util.ControllerUtils;


@Service
public class PerformanceAttributionServiceImpl extends GenericServiceImpl<PerformanceAttribution, Integer> implements PerformanceAttributionService {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceAttributionServiceImpl.class);

	@Autowired
	PerformanceAttributionDAO performanceAttributionDao;

	

	@Override
	public GenericDAO<PerformanceAttribution, Integer> getDAO() {
		return (GenericDAO<PerformanceAttribution, Integer>) performanceAttributionDao;
	}
	
	public List<PerformanceAttribution> findAll () {
		List<PerformanceAttribution> performanceAttributions = performanceAttributionDao.findAll();
		
		return performanceAttributions;	
		
	}

	public ResultDTO addPerformanceAttribution(PerformanceAttributionDTO performanceAttributionDTO, RequestDTO requestDTO) {

		PerformanceAttribution performanceAttribution = new PerformanceAttribution();

		performanceAttribution.setPerformanceAttributionId(performanceAttributionDTO.getPerformanceAttributionId());

		performanceAttribution.setPerformanceReportId(performanceAttributionDTO.getPerformanceReportId());

		performanceAttribution.setAttributionType(performanceAttributionDTO.getAttributionType());

		performanceAttribution.setAttributionReference(performanceAttributionDTO.getAttributionReference());

		performanceAttribution.setContribution(performanceAttributionDTO.getContribution());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		performanceAttribution = performanceAttributionDao.save(performanceAttribution);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PerformanceAttribution> getAllPerformanceAttributions(Pageable pageable) {
		return performanceAttributionDao.findAll(pageable);
	}

	public Page<PerformanceAttribution> getAllPerformanceAttributions(Specification<PerformanceAttribution> spec, Pageable pageable) {
		return performanceAttributionDao.findAll(spec, pageable);
	}

	public ResponseEntity<PerformanceAttributionPageDTO> getPerformanceAttributions(PerformanceAttributionSearchDTO performanceAttributionSearchDTO) {
	
			Integer performanceAttributionId = performanceAttributionSearchDTO.getPerformanceAttributionId(); 
 			Integer performanceReportId = performanceAttributionSearchDTO.getPerformanceReportId(); 
 			String attributionType = performanceAttributionSearchDTO.getAttributionType(); 
 			String attributionReference = performanceAttributionSearchDTO.getAttributionReference(); 
  			String sortBy = performanceAttributionSearchDTO.getSortBy();
			String sortOrder = performanceAttributionSearchDTO.getSortOrder();
			String searchQuery = performanceAttributionSearchDTO.getSearchQuery();
			Integer page = performanceAttributionSearchDTO.getPage();
			Integer size = performanceAttributionSearchDTO.getSize();

	        Specification<PerformanceAttribution> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, performanceAttributionId, "performanceAttributionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, performanceReportId, "performanceReportId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, attributionType, "attributionType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, attributionReference, "attributionReference"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("attributionType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("attributionReference")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PerformanceAttribution> performanceAttributions = this.getAllPerformanceAttributions(spec, pageable);
		
		//System.out.println(String.valueOf(performanceAttributions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(performanceAttributions.getTotalPages()));
		
		List<PerformanceAttribution> performanceAttributionsList = performanceAttributions.getContent();
		
		PerformanceAttributionConvertCriteriaDTO convertCriteria = new PerformanceAttributionConvertCriteriaDTO();
		List<PerformanceAttributionDTO> performanceAttributionDTOs = this.convertPerformanceAttributionsToPerformanceAttributionDTOs(performanceAttributionsList,convertCriteria);
		
		PerformanceAttributionPageDTO performanceAttributionPageDTO = new PerformanceAttributionPageDTO();
		performanceAttributionPageDTO.setPerformanceAttributions(performanceAttributionDTOs);
		performanceAttributionPageDTO.setTotalElements(performanceAttributions.getTotalElements());
		return ResponseEntity.ok(performanceAttributionPageDTO);
	}

	public List<PerformanceAttributionDTO> convertPerformanceAttributionsToPerformanceAttributionDTOs(List<PerformanceAttribution> performanceAttributions, PerformanceAttributionConvertCriteriaDTO convertCriteria) {
		
		List<PerformanceAttributionDTO> performanceAttributionDTOs = new ArrayList<PerformanceAttributionDTO>();
		
		for (PerformanceAttribution performanceAttribution : performanceAttributions) {
			performanceAttributionDTOs.add(convertPerformanceAttributionToPerformanceAttributionDTO(performanceAttribution,convertCriteria));
		}
		
		return performanceAttributionDTOs;

	}
	
	public PerformanceAttributionDTO convertPerformanceAttributionToPerformanceAttributionDTO(PerformanceAttribution performanceAttribution, PerformanceAttributionConvertCriteriaDTO convertCriteria) {
		
		PerformanceAttributionDTO performanceAttributionDTO = new PerformanceAttributionDTO();

		performanceAttributionDTO.setPerformanceAttributionId(performanceAttribution.getPerformanceAttributionId());

		performanceAttributionDTO.setPerformanceReportId(performanceAttribution.getPerformanceReportId());

		performanceAttributionDTO.setAttributionType(performanceAttribution.getAttributionType());

		performanceAttributionDTO.setAttributionReference(performanceAttribution.getAttributionReference());

		performanceAttributionDTO.setContribution(performanceAttribution.getContribution());
		
		return performanceAttributionDTO;
	}

	public ResultDTO updatePerformanceAttribution(PerformanceAttributionDTO performanceAttributionDTO, RequestDTO requestDTO) {
		
		PerformanceAttribution performanceAttribution = performanceAttributionDao.getById(performanceAttributionDTO.getPerformanceAttributionId());
		
		performanceAttribution.setPerformanceAttributionId(ControllerUtils.setValue(performanceAttribution.getPerformanceAttributionId(), performanceAttributionDTO.getPerformanceAttributionId()));
		
		performanceAttribution.setPerformanceReportId(ControllerUtils.setValue(performanceAttribution.getPerformanceReportId(), performanceAttributionDTO.getPerformanceReportId()));
		
		performanceAttribution.setAttributionType(ControllerUtils.setValue(performanceAttribution.getAttributionType(), performanceAttributionDTO.getAttributionType()));
		
		performanceAttribution.setAttributionReference(ControllerUtils.setValue(performanceAttribution.getAttributionReference(), performanceAttributionDTO.getAttributionReference()));
		
		performanceAttribution.setContribution(ControllerUtils.setValue(performanceAttribution.getContribution(), performanceAttributionDTO.getContribution()));

        performanceAttribution = performanceAttributionDao.save(performanceAttribution);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PerformanceAttributionDTO getPerformanceAttributionDTOById(Integer performanceAttributionId) {
	
		PerformanceAttribution performanceAttribution = performanceAttributionDao.getById(performanceAttributionId);
		
		PerformanceAttributionConvertCriteriaDTO convertCriteria = new PerformanceAttributionConvertCriteriaDTO();
		return(this.convertPerformanceAttributionToPerformanceAttributionDTO(performanceAttribution,convertCriteria));
	}

}
