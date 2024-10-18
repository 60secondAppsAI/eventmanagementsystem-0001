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

import com.eventmanagementsystem.domain.Session;
import com.eventmanagementsystem.dto.SessionDTO;
import com.eventmanagementsystem.dto.SessionSearchDTO;
import com.eventmanagementsystem.dto.SessionPageDTO;
import com.eventmanagementsystem.service.SessionService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/session")
@RestController
public class SessionController {

	private final static Logger logger = LoggerFactory.getLogger(SessionController.class);

	@Autowired
	SessionService sessionService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Session> getAll() {

		List<Session> sessions = sessionService.findAll();
		
		return sessions;	
	}

	//@ReadAccess
	@GetMapping(value = "/{sessionId}")
	@ResponseBody
	public SessionDTO getSession(@PathVariable Integer sessionId) {
		
		return (sessionService.getSessionDTOById(sessionId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addSession", method = RequestMethod.POST)
	public ResponseEntity<?> addSession(@RequestBody SessionDTO sessionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sessionService.addSession(sessionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/sessions")
	public ResponseEntity<SessionPageDTO> getSessions(SessionSearchDTO sessionSearchDTO) {
 
		return sessionService.getSessions(sessionSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateSession", method = RequestMethod.POST)
	public ResponseEntity<?> updateSession(@RequestBody SessionDTO sessionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sessionService.updateSession(sessionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
