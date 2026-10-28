CREATE DATABASE IF NOT EXISTS PerformanceApp;

CREATE TABLE PerformanceApp.performance_reports (
    `performance_report_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `start_date` DATETIME,
    `end_date` DATETIME,
    `total_return` DOUBLE,
    `benchmark_return` DOUBLE,
PRIMARY KEY (performance_report_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PerformanceApp.performance_metrics (
    `performance_metric_id` INT AUTO_INCREMENT,
    `performance_report_id` INT,
    `metric_name` VARCHAR(255),
    `metric_value` DOUBLE,
PRIMARY KEY (performance_metric_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PerformanceApp.performance_periods (
    `performance_period_id` INT AUTO_INCREMENT,
    `performance_report_id` INT,
    `period_date` DATETIME,
    `portfolio_return` DOUBLE,
    `benchmark_return` DOUBLE,
PRIMARY KEY (performance_period_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PerformanceApp.performance_attributions (
    `performance_attribution_id` INT AUTO_INCREMENT,
    `performance_report_id` INT,
    `attribution_type` VARCHAR(255),
    `attribution_reference` VARCHAR(255),
    `contribution` DOUBLE,
PRIMARY KEY (performance_attribution_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE PerformanceApp.drawdown_periods (
    `drawdown_period_id` INT AUTO_INCREMENT,
    `performance_report_id` INT,
    `start_date` DATETIME,
    `end_date` DATETIME,
    `maximum_drawdown` DOUBLE,
PRIMARY KEY (drawdown_period_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
