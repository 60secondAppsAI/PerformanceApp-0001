package com.PerformanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePerformanceMetricDTO {

	private Integer ownerId;

	private Integer performanceMetricId;

	private Integer performanceMetricStatus;
	
	private Integer nextOrPrevious;
}

