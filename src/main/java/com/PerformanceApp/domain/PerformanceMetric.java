package com.PerformanceApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="performance_metrics")
@Getter @Setter @NoArgsConstructor
public class PerformanceMetric {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="performance_metric_id")
	private Integer performanceMetricId;
    
  	@Column(name="performance_report_id")
	private Integer performanceReportId;
    
  	@Column(name="metric_name")
	private String metricName;
    
  	@Column(name="metric_value")
	private Double metricValue;
    

}
