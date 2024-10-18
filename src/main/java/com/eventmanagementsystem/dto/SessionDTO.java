package com.eventmanagementsystem.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SessionDTO {

	private Integer sessionId;

	private String title;

	private String speaker;

	private Time startTime;

	private Time endTime;






}
