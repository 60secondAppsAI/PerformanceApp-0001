package com.PerformanceApp.dao;

import java.util.List;
import java.util.Date;

import com.PerformanceApp.dao.GenericDAO;
import com.PerformanceApp.domain.DrawdownPeriod;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface DrawdownPeriodDAO extends GenericDAO<DrawdownPeriod, Integer> {
  
	List<DrawdownPeriod> findAll();
	


}

