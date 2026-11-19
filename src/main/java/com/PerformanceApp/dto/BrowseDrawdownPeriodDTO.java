package com.PerformanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseDrawdownPeriodDTO {

	private Integer ownerId;

	private Integer drawdownPeriodId;

	private Integer drawdownPeriodStatus;
	
	private Integer nextOrPrevious;
}

