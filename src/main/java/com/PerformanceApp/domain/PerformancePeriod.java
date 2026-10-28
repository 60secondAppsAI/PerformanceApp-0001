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
@Table(name="performance_periods")
@Getter @Setter @NoArgsConstructor
public class PerformancePeriod {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="performance_period_id")
	private Integer performancePeriodId;
    
  	@Column(name="performance_report_id")
	private Integer performanceReportId;
    
  	@Column(name="period_date")
	private Date periodDate;
    
  	@Column(name="portfolio_return")
	private Double portfolioReturn;
    
  	@Column(name="benchmark_return")
	private Double benchmarkReturn;
    

}
