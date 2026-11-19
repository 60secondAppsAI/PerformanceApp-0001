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

import com.PerformanceApp.domain.PerformanceReport;
import com.PerformanceApp.dto.PerformanceReportDTO;
import com.PerformanceApp.dto.PerformanceReportSearchDTO;
import com.PerformanceApp.dto.PerformanceReportPageDTO;
import com.PerformanceApp.service.PerformanceReportService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/performanceReport")
@RestController
public class PerformanceReportController {

	private final static Logger logger = LoggerFactory.getLogger(PerformanceReportController.class);

	@Autowired
	PerformanceReportService performanceReportService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PerformanceReport> getAll() {

		List<PerformanceReport> performanceReports = performanceReportService.findAll();
		
		return performanceReports;	
	}

	@GetMapping(value = "/{performanceReportId}")
	@ResponseBody
	public PerformanceReportDTO getPerformanceReport(@PathVariable Integer performanceReportId) {
		
		return (performanceReportService.getPerformanceReportDTOById(performanceReportId));
	}

 	@RequestMapping(value = "/addPerformanceReport", method = RequestMethod.POST)
	public ResponseEntity<?> addPerformanceReport(@RequestBody PerformanceReportDTO performanceReportDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceReportService.addPerformanceReport(performanceReportDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/performanceReports")
	public ResponseEntity<PerformanceReportPageDTO> getPerformanceReports(PerformanceReportSearchDTO performanceReportSearchDTO) {
 
		return performanceReportService.getPerformanceReports(performanceReportSearchDTO);
	}	

	@RequestMapping(value = "/updatePerformanceReport", method = RequestMethod.POST)
	public ResponseEntity<?> updatePerformanceReport(@RequestBody PerformanceReportDTO performanceReportDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = performanceReportService.updatePerformanceReport(performanceReportDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
