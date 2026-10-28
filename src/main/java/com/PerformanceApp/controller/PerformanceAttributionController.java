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

import com.PerformanceApp.domain.PerformanceAttribution;
import com.PerformanceApp.dto.PerformanceAttributionDTO;
import com.PerformanceApp.dto.PerformanceAttributionSearchDTO;
import com.PerformanceApp.dto.PerformanceAttributionPageDTO;
import com.PerformanceApp.service.PerformanceAttributionService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/performanceAttribution")
@RestController
public class PerformanceAttributionController {

	private final static Logger logger = LoggerFactory.getLogger(PerformanceAttributionController.class);

	@Autowired
	PerformanceAttributionService performanceAttributionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PerformanceAttribution> getAll() {

		List<PerformanceAttribution> performanceAttributions = performanceAttributionService.findAll();
		
		return performanceAttributions;	
	}

	@GetMapping(value = "/{performanceAttributionId}")
	@ResponseBody
	public PerformanceAttributionDTO getPerformanceAttribution(@PathVariable Integer performanceAttributionId) {
		
		return (performanceAttributionService.getPerformanceAttributionDTOById(performanceAttributionId));
	}

 	@RequestMapping(value = "/addPerformanceAttribution", method = RequestMethod.POST)
	public ResponseEntity<?> addPerformanceAttribution(@RequestBody PerformanceAttributionDTO performanceAttributionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceAttributionService.addPerformanceAttribution(performanceAttributionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/performanceAttributions")
	public ResponseEntity<PerformanceAttributionPageDTO> getPerformanceAttributions(PerformanceAttributionSearchDTO performanceAttributionSearchDTO) {
 
		return performanceAttributionService.getPerformanceAttributions(performanceAttributionSearchDTO);
	}	

	@RequestMapping(value = "/updatePerformanceAttribution", method = RequestMethod.POST)
	public ResponseEntity<?> updatePerformanceAttribution(@RequestBody PerformanceAttributionDTO performanceAttributionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceAttributionService.updatePerformanceAttribution(performanceAttributionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
