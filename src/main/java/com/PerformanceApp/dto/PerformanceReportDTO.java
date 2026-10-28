package com.PerformanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PerformanceReportDTO {

	private Integer performanceReportId;

	private Integer portfolioId;

	private Date startDate;

	private Date endDate;

	private Double totalReturn;

	private Double benchmarkReturn;






}
