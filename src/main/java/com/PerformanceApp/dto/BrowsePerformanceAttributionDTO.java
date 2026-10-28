package com.PerformanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePerformanceAttributionDTO {

	private Integer ownerId;

	private Integer performanceAttributionId;

	private Integer performanceAttributionStatus;
	
	private Integer nextOrPrevious;
}

