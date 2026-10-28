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
import com.PerformanceApp.dao.DrawdownPeriodDAO;
import com.PerformanceApp.domain.DrawdownPeriod;
import com.PerformanceApp.dto.DrawdownPeriodDTO;
import com.PerformanceApp.dto.DrawdownPeriodSearchDTO;
import com.PerformanceApp.dto.DrawdownPeriodPageDTO;
import com.PerformanceApp.dto.DrawdownPeriodConvertCriteriaDTO;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;
import com.PerformanceApp.service.DrawdownPeriodService;
import com.PerformanceApp.util.ControllerUtils;


@Service
public class DrawdownPeriodServiceImpl extends GenericServiceImpl<DrawdownPeriod, Integer> implements DrawdownPeriodService {

    private final static Logger logger = LoggerFactory.getLogger(DrawdownPeriodServiceImpl.class);

	@Autowired
	DrawdownPeriodDAO drawdownPeriodDao;

	

	@Override
	public GenericDAO<DrawdownPeriod, Integer> getDAO() {
		return (GenericDAO<DrawdownPeriod, Integer>) drawdownPeriodDao;
	}
	
	public List<DrawdownPeriod> findAll () {
		List<DrawdownPeriod> drawdownPeriods = drawdownPeriodDao.findAll();
		
		return drawdownPeriods;	
		
	}

	public ResultDTO addDrawdownPeriod(DrawdownPeriodDTO drawdownPeriodDTO, RequestDTO requestDTO) {

		DrawdownPeriod drawdownPeriod = new DrawdownPeriod();

		drawdownPeriod.setDrawdownPeriodId(drawdownPeriodDTO.getDrawdownPeriodId());

		drawdownPeriod.setPerformanceReportId(drawdownPeriodDTO.getPerformanceReportId());

		drawdownPeriod.setStartDate(drawdownPeriodDTO.getStartDate());

		drawdownPeriod.setEndDate(drawdownPeriodDTO.getEndDate());

		drawdownPeriod.setMaximumDrawdown(drawdownPeriodDTO.getMaximumDrawdown());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		drawdownPeriod = drawdownPeriodDao.save(drawdownPeriod);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<DrawdownPeriod> getAllDrawdownPeriods(Pageable pageable) {
		return drawdownPeriodDao.findAll(pageable);
	}

	public Page<DrawdownPeriod> getAllDrawdownPeriods(Specification<DrawdownPeriod> spec, Pageable pageable) {
		return drawdownPeriodDao.findAll(spec, pageable);
	}

	public ResponseEntity<DrawdownPeriodPageDTO> getDrawdownPeriods(DrawdownPeriodSearchDTO drawdownPeriodSearchDTO) {
	
			Integer drawdownPeriodId = drawdownPeriodSearchDTO.getDrawdownPeriodId(); 
 			Integer performanceReportId = drawdownPeriodSearchDTO.getPerformanceReportId(); 
 			Date startDate = drawdownPeriodSearchDTO.getStartDate();
 			Date endDate = drawdownPeriodSearchDTO.getEndDate();
  			String sortBy = drawdownPeriodSearchDTO.getSortBy();
			String sortOrder = drawdownPeriodSearchDTO.getSortOrder();
			String searchQuery = drawdownPeriodSearchDTO.getSearchQuery();
			Integer page = drawdownPeriodSearchDTO.getPage();
			Integer size = drawdownPeriodSearchDTO.getSize();

	        Specification<DrawdownPeriod> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, drawdownPeriodId, "drawdownPeriodId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, performanceReportId, "performanceReportId"); 
			
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

		Page<DrawdownPeriod> drawdownPeriods = this.getAllDrawdownPeriods(spec, pageable);
		
		//System.out.println(String.valueOf(drawdownPeriods.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(drawdownPeriods.getTotalPages()));
		
		List<DrawdownPeriod> drawdownPeriodsList = drawdownPeriods.getContent();
		
		DrawdownPeriodConvertCriteriaDTO convertCriteria = new DrawdownPeriodConvertCriteriaDTO();
		List<DrawdownPeriodDTO> drawdownPeriodDTOs = this.convertDrawdownPeriodsToDrawdownPeriodDTOs(drawdownPeriodsList,convertCriteria);
		
		DrawdownPeriodPageDTO drawdownPeriodPageDTO = new DrawdownPeriodPageDTO();
		drawdownPeriodPageDTO.setDrawdownPeriods(drawdownPeriodDTOs);
		drawdownPeriodPageDTO.setTotalElements(drawdownPeriods.getTotalElements());
		return ResponseEntity.ok(drawdownPeriodPageDTO);
	}

	public List<DrawdownPeriodDTO> convertDrawdownPeriodsToDrawdownPeriodDTOs(List<DrawdownPeriod> drawdownPeriods, DrawdownPeriodConvertCriteriaDTO convertCriteria) {
		
		List<DrawdownPeriodDTO> drawdownPeriodDTOs = new ArrayList<DrawdownPeriodDTO>();
		
		for (DrawdownPeriod drawdownPeriod : drawdownPeriods) {
			drawdownPeriodDTOs.add(convertDrawdownPeriodToDrawdownPeriodDTO(drawdownPeriod,convertCriteria));
		}
		
		return drawdownPeriodDTOs;

	}
	
	public DrawdownPeriodDTO convertDrawdownPeriodToDrawdownPeriodDTO(DrawdownPeriod drawdownPeriod, DrawdownPeriodConvertCriteriaDTO convertCriteria) {
		
		DrawdownPeriodDTO drawdownPeriodDTO = new DrawdownPeriodDTO();

		drawdownPeriodDTO.setDrawdownPeriodId(drawdownPeriod.getDrawdownPeriodId());

		drawdownPeriodDTO.setPerformanceReportId(drawdownPeriod.getPerformanceReportId());

		drawdownPeriodDTO.setStartDate(drawdownPeriod.getStartDate());

		drawdownPeriodDTO.setEndDate(drawdownPeriod.getEndDate());

		drawdownPeriodDTO.setMaximumDrawdown(drawdownPeriod.getMaximumDrawdown());
		
		return drawdownPeriodDTO;
	}

	public ResultDTO updateDrawdownPeriod(DrawdownPeriodDTO drawdownPeriodDTO, RequestDTO requestDTO) {
		
		DrawdownPeriod drawdownPeriod = drawdownPeriodDao.getById(drawdownPeriodDTO.getDrawdownPeriodId());
		
		drawdownPeriod.setDrawdownPeriodId(ControllerUtils.setValue(drawdownPeriod.getDrawdownPeriodId(), drawdownPeriodDTO.getDrawdownPeriodId()));
		
		drawdownPeriod.setPerformanceReportId(ControllerUtils.setValue(drawdownPeriod.getPerformanceReportId(), drawdownPeriodDTO.getPerformanceReportId()));
		
		drawdownPeriod.setStartDate(ControllerUtils.setValue(drawdownPeriod.getStartDate(), drawdownPeriodDTO.getStartDate()));
		
		drawdownPeriod.setEndDate(ControllerUtils.setValue(drawdownPeriod.getEndDate(), drawdownPeriodDTO.getEndDate()));
		
		drawdownPeriod.setMaximumDrawdown(ControllerUtils.setValue(drawdownPeriod.getMaximumDrawdown(), drawdownPeriodDTO.getMaximumDrawdown()));

        drawdownPeriod = drawdownPeriodDao.save(drawdownPeriod);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DrawdownPeriodDTO getDrawdownPeriodDTOById(Integer drawdownPeriodId) {
	
		DrawdownPeriod drawdownPeriod = drawdownPeriodDao.getById(drawdownPeriodId);
		
		DrawdownPeriodConvertCriteriaDTO convertCriteria = new DrawdownPeriodConvertCriteriaDTO();
		return(this.convertDrawdownPeriodToDrawdownPeriodDTO(drawdownPeriod,convertCriteria));
	}

}
