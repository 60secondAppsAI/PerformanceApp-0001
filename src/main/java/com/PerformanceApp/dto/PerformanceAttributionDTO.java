package com.PerformanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PerformanceAttributionDTO {

	private Integer performanceAttributionId;

	private Integer performanceReportId;

	private String attributionType;

	private String attributionReference;

	private Double contribution;






}
