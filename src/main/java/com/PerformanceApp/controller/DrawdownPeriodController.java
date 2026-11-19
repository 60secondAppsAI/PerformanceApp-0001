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

import com.PerformanceApp.domain.DrawdownPeriod;
import com.PerformanceApp.dto.DrawdownPeriodDTO;
import com.PerformanceApp.dto.DrawdownPeriodSearchDTO;
import com.PerformanceApp.dto.DrawdownPeriodPageDTO;
import com.PerformanceApp.service.DrawdownPeriodService;
import com.PerformanceApp.dto.common.RequestDTO;
import com.PerformanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/drawdownPeriod")
@RestController
public class DrawdownPeriodController {

	private final static Logger logger = LoggerFactory.getLogger(DrawdownPeriodController.class);

	@Autowired
	DrawdownPeriodService drawdownPeriodService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<DrawdownPeriod> getAll() {

		List<DrawdownPeriod> drawdownPeriods = drawdownPeriodService.findAll();
		
		return drawdownPeriods;	
	}

	@GetMapping(value = "/{drawdownPeriodId}")
	@ResponseBody
	public DrawdownPeriodDTO getDrawdownPeriod(@PathVariable Integer drawdownPeriodId) {
		
		return (drawdownPeriodService.getDrawdownPeriodDTOById(drawdownPeriodId));
	}

 	@RequestMapping(value = "/addDrawdownPeriod", method = RequestMethod.POST)
	public ResponseEntity<?> addDrawdownPeriod(@RequestBody DrawdownPeriodDTO drawdownPeriodDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = drawdownPeriodService.addDrawdownPeriod(drawdownPeriodDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/drawdownPeriods")
	public ResponseEntity<DrawdownPeriodPageDTO> getDrawdownPeriods(DrawdownPeriodSearchDTO drawdownPeriodSearchDTO) {
 
		return drawdownPeriodService.getDrawdownPeriods(drawdownPeriodSearchDTO);
	}	

	@RequestMapping(value = "/updateDrawdownPeriod", method = RequestMethod.POST)
	public ResponseEntity<?> updateDrawdownPeriod(@RequestBody DrawdownPeriodDTO drawdownPeriodDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = drawdownPeriodService.updateDrawdownPeriod(drawdownPeriodDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
