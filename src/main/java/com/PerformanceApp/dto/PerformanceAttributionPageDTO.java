package com.PerformanceApp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PerformanceAttributionPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<PerformanceAttributionDTO> performanceAttributions;
}





