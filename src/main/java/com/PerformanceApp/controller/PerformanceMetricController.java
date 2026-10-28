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

import com.PerformanceApp.domain.PerformanceMetric;
import com.PerformanceApp.dto.PerformanceMetricDTO;
import com.PerformanceApp.dto.PerformanceMetricSearchDTO;
import com.PerformanceApp.dto.PerformanceMetricPageDTO;
import com.PerformanceApp.service.PerformanceMetricService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/performanceMetric")
@RestController
public class PerformanceMetricController {

	private final static Logger logger = LoggerFactory.getLogger(PerformanceMetricController.class);

	@Autowired
	PerformanceMetricService performanceMetricService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PerformanceMetric> getAll() {

		List<PerformanceMetric> performanceMetrics = performanceMetricService.findAll();
		
		return performanceMetrics;	
	}

	@GetMapping(value = "/{performanceMetricId}")
	@ResponseBody
	public PerformanceMetricDTO getPerformanceMetric(@PathVariable Integer performanceMetricId) {
		
		return (performanceMetricService.getPerformanceMetricDTOById(performanceMetricId));
	}

 	@RequestMapping(value = "/addPerformanceMetric", method = RequestMethod.POST)
	public ResponseEntity<?> addPerformanceMetric(@RequestBody PerformanceMetricDTO performanceMetricDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceMetricService.addPerformanceMetric(performanceMetricDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/performanceMetrics")
	public ResponseEntity<PerformanceMetricPageDTO> getPerformanceMetrics(PerformanceMetricSearchDTO performanceMetricSearchDTO) {
 
		return performanceMetricService.getPerformanceMetrics(performanceMetricSearchDTO);
	}	

	@RequestMapping(value = "/updatePerformanceMetric", method = RequestMethod.POST)
	public ResponseEntity<?> updatePerformanceMetric(@RequestBody PerformanceMetricDTO performanceMetricDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceMetricService.updatePerformanceMetric(performanceMetricDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
