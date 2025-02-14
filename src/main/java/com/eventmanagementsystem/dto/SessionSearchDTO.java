package com.eventmanagementsystem.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer sessionId;
	
	private String title;
	
	private String speaker;
	
	private Time startTime;
	
	private Time endTime;
	
}
