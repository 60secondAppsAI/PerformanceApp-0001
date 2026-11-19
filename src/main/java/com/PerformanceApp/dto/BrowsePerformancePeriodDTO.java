package com.PerformanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePerformancePeriodDTO {

	private Integer ownerId;

	private Integer performancePeriodId;

	private Integer performancePeriodStatus;
	
	private Integer nextOrPrevious;
}

