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

import com.eventmanagementsystem.domain.Sponsor;
import com.eventmanagementsystem.dto.SponsorDTO;
import com.eventmanagementsystem.dto.SponsorSearchDTO;
import com.eventmanagementsystem.dto.SponsorPageDTO;
import com.eventmanagementsystem.service.SponsorService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/sponsor")
@RestController
public class SponsorController {

	private final static Logger logger = LoggerFactory.getLogger(SponsorController.class);

	@Autowired
	SponsorService sponsorService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Sponsor> getAll() {

		List<Sponsor> sponsors = sponsorService.findAll();
		
		return sponsors;	
	}

	//@ReadAccess
	@GetMapping(value = "/{sponsorId}")
	@ResponseBody
	public SponsorDTO getSponsor(@PathVariable Integer sponsorId) {
		
		return (sponsorService.getSponsorDTOById(sponsorId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addSponsor", method = RequestMethod.POST)
	public ResponseEntity<?> addSponsor(@RequestBody SponsorDTO sponsorDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sponsorService.addSponsor(sponsorDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/sponsors")
	public ResponseEntity<SponsorPageDTO> getSponsors(SponsorSearchDTO sponsorSearchDTO) {
 
		return sponsorService.getSponsors(sponsorSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateSponsor", method = RequestMethod.POST)
	public ResponseEntity<?> updateSponsor(@RequestBody SponsorDTO sponsorDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sponsorService.updateSponsor(sponsorDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
