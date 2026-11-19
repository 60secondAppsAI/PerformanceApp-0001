package com.PerformanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PerformancePeriodDTO {

	private Integer performancePeriodId;

	private Integer performanceReportId;

	private Date periodDate;

	private Double portfolioReturn;

	private Double benchmarkReturn;






}
