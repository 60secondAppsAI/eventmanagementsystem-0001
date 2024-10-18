package com.eventmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.eventmanagementsystem.domain.Attendee;
import com.eventmanagementsystem.dto.AttendeeDTO;
import com.eventmanagementsystem.dto.AttendeeSearchDTO;
import com.eventmanagementsystem.dto.AttendeePageDTO;
import com.eventmanagementsystem.dto.AttendeeConvertCriteriaDTO;
import com.eventmanagementsystem.service.GenericService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AttendeeService extends GenericService<Attendee, Integer> {

	List<Attendee> findAll();

	ResultDTO addAttendee(AttendeeDTO attendeeDTO, RequestDTO requestDTO);

	ResultDTO updateAttendee(AttendeeDTO attendeeDTO, RequestDTO requestDTO);

    Page<Attendee> getAllAttendees(Pageable pageable);

    Page<Attendee> getAllAttendees(Specification<Attendee> spec, Pageable pageable);

	ResponseEntity<AttendeePageDTO> getAttendees(AttendeeSearchDTO attendeeSearchDTO);
	
	List<AttendeeDTO> convertAttendeesToAttendeeDTOs(List<Attendee> attendees, AttendeeConvertCriteriaDTO convertCriteria);

	AttendeeDTO getAttendeeDTOById(Integer attendeeId);







}





