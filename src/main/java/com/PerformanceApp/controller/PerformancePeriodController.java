package com.PerformanceApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.PerformanceApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.PerformanceApp.domain.PerformancePeriod;
import com.PerformanceApp.dto.PerformancePeriodDTO;
import com.PerformanceApp.dto.PerformancePeriodSearchDTO;
import com.PerformanceApp.dto.PerformancePeriodPageDTO;
import com.PerformanceApp.service.PerformancePeriodService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/performancePeriod")
@RestController
public class PerformancePeriodController {

	private final static Logger logger = LoggerFactory.getLogger(PerformancePeriodController.class);

	@Autowired
	PerformancePeriodService performancePeriodService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PerformancePeriod> getAll() {

		List<PerformancePeriod> performancePeriods = performancePeriodService.findAll();
		
		return performancePeriods;	
	}

	@GetMapping(value = "/{performancePeriodId}")
	@ResponseBody
	public PerformancePeriodDTO getPerformancePeriod(@PathVariable Integer performancePeriodId) {
		
		return (performancePeriodService.getPerformancePeriodDTOById(performancePeriodId));
	}

 	@RequestMapping(value = "/addPerformancePeriod", method = RequestMethod.POST)
	public ResponseEntity<?> addPerformancePeriod(@RequestBody PerformancePeriodDTO performancePeriodDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performancePeriodService.addPerformancePeriod(performancePeriodDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/performancePeriods")
	public ResponseEntity<PerformancePeriodPageDTO> getPerformancePeriods(PerformancePeriodSearchDTO performancePeriodSearchDTO) {
 
		return performancePeriodService.getPerformancePeriods(performancePeriodSearchDTO);
	}	

	@RequestMapping(value = "/updatePerformancePeriod", method = RequestMethod.POST)
	public ResponseEntity<?> updatePerformancePeriod(@RequestBody PerformancePeriodDTO performancePeriodDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performancePeriodService.updatePerformancePeriod(performancePeriodDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
