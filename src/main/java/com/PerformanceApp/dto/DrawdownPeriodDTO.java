package com.PerformanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DrawdownPeriodDTO {

	private Integer drawdownPeriodId;

	private Integer performanceReportId;

	private Date startDate;

	private Date endDate;

	private Double maximumDrawdown;






}
