package com.PerformanceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DrawdownPeriodSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer drawdownPeriodId;
	
	private Integer performanceReportId;
	
	private Date startDate;
	
	private Date endDate;
	
	private Double maximumDrawdown;
	
}
