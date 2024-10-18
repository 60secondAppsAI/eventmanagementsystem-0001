package com.eventmanagementsystem.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttendeeSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer attendeeId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
}
