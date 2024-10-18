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

import com.eventmanagementsystem.domain.Organizer;
import com.eventmanagementsystem.dto.OrganizerDTO;
import com.eventmanagementsystem.dto.OrganizerSearchDTO;
import com.eventmanagementsystem.dto.OrganizerPageDTO;
import com.eventmanagementsystem.service.OrganizerService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/organizer")
@RestController
public class OrganizerController {

	private final static Logger logger = LoggerFactory.getLogger(OrganizerController.class);

	@Autowired
	OrganizerService organizerService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Organizer> getAll() {

		List<Organizer> organizers = organizerService.findAll();
		
		return organizers;	
	}

	//@ReadAccess
	@GetMapping(value = "/{organizerId}")
	@ResponseBody
	public OrganizerDTO getOrganizer(@PathVariable Integer organizerId) {
		
		return (organizerService.getOrganizerDTOById(organizerId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addOrganizer", method = RequestMethod.POST)
	public ResponseEntity<?> addOrganizer(@RequestBody OrganizerDTO organizerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = organizerService.addOrganizer(organizerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/organizers")
	public ResponseEntity<OrganizerPageDTO> getOrganizers(OrganizerSearchDTO organizerSearchDTO) {
 
		return organizerService.getOrganizers(organizerSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateOrganizer", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrganizer(@RequestBody OrganizerDTO organizerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = organizerService.updateOrganizer(organizerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
