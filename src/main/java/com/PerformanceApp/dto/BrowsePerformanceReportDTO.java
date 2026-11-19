package com.PerformanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePerformanceReportDTO {

	private Integer ownerId;

	private Integer performanceReportId;

	private Integer performanceReportStatus;
	
	private Integer nextOrPrevious;
}

