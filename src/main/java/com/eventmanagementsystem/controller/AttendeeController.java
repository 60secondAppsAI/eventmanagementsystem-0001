package com.eventmanagementsystem.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.eventmanagementsystem.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.eventmanagementsystem.domain.Attendee;
import com.eventmanagementsystem.dto.AttendeeDTO;
import com.eventmanagementsystem.dto.AttendeeSearchDTO;
import com.eventmanagementsystem.dto.AttendeePageDTO;
import com.eventmanagementsystem.service.AttendeeService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/attendee")
@RestController
public class AttendeeController {

	private final static Logger logger = LoggerFactory.getLogger(AttendeeController.class);

	@Autowired
	AttendeeService attendeeService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Attendee> getAll() {

		List<Attendee> attendees = attendeeService.findAll();
		
		return attendees;	
	}

	//@ReadAccess
	@GetMapping(value = "/{attendeeId}")
	@ResponseBody
	public AttendeeDTO getAttendee(@PathVariable Integer attendeeId) {
		
		return (attendeeService.getAttendeeDTOById(attendeeId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addAttendee", method = RequestMethod.POST)
	public ResponseEntity<?> addAttendee(@RequestBody AttendeeDTO attendeeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = attendeeService.addAttendee(attendeeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/attendees")
	public ResponseEntity<AttendeePageDTO> getAttendees(AttendeeSearchDTO attendeeSearchDTO) {
 
		return attendeeService.getAttendees(attendeeSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateAttendee", method = RequestMethod.POST)
	public ResponseEntity<?> updateAttendee(@RequestBody AttendeeDTO attendeeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = attendeeService.updateAttendee(attendeeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
