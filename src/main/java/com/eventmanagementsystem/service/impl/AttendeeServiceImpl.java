package com.eventmanagementsystem.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.service.GenericService;
import com.eventmanagementsystem.service.impl.GenericServiceImpl;
import com.eventmanagementsystem.dao.AttendeeDAO;
import com.eventmanagementsystem.domain.Attendee;
import com.eventmanagementsystem.dto.AttendeeDTO;
import com.eventmanagementsystem.dto.AttendeeSearchDTO;
import com.eventmanagementsystem.dto.AttendeePageDTO;
import com.eventmanagementsystem.dto.AttendeeConvertCriteriaDTO;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import com.eventmanagementsystem.service.AttendeeService;
import com.eventmanagementsystem.util.ControllerUtils;





@Service
public class AttendeeServiceImpl extends GenericServiceImpl<Attendee, Integer> implements AttendeeService {

    private final static Logger logger = LoggerFactory.getLogger(AttendeeServiceImpl.class);

	@Autowired
	AttendeeDAO attendeeDao;

	


	@Override
	public GenericDAO<Attendee, Integer> getDAO() {
		return (GenericDAO<Attendee, Integer>) attendeeDao;
	}
	
	public List<Attendee> findAll () {
		List<Attendee> attendees = attendeeDao.findAll();
		
		return attendees;	
		
	}

	public ResultDTO addAttendee(AttendeeDTO attendeeDTO, RequestDTO requestDTO) {

		Attendee attendee = new Attendee();

		attendee.setAttendeeId(attendeeDTO.getAttendeeId());


		attendee.setFirstName(attendeeDTO.getFirstName());


		attendee.setLastName(attendeeDTO.getLastName());


		attendee.setEmail(attendeeDTO.getEmail());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		attendee = attendeeDao.save(attendee);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Attendee> getAllAttendees(Pageable pageable) {
		return attendeeDao.findAll(pageable);
	}

	public Page<Attendee> getAllAttendees(Specification<Attendee> spec, Pageable pageable) {
		return attendeeDao.findAll(spec, pageable);
	}

	public ResponseEntity<AttendeePageDTO> getAttendees(AttendeeSearchDTO attendeeSearchDTO) {
	
			Integer attendeeId = attendeeSearchDTO.getAttendeeId(); 
 			String firstName = attendeeSearchDTO.getFirstName(); 
 			String lastName = attendeeSearchDTO.getLastName(); 
 			String email = attendeeSearchDTO.getEmail(); 
 			String sortBy = attendeeSearchDTO.getSortBy();
			String sortOrder = attendeeSearchDTO.getSortOrder();
			String searchQuery = attendeeSearchDTO.getSearchQuery();
			Integer page = attendeeSearchDTO.getPage();
			Integer size = attendeeSearchDTO.getSize();

	        Specification<Attendee> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, attendeeId, "attendeeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, firstName, "firstName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, lastName, "lastName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, email, "email"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("firstName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("lastName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Attendee> attendees = this.getAllAttendees(spec, pageable);
		
		//System.out.println(String.valueOf(attendees.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(attendees.getTotalPages()));
		
		List<Attendee> attendeesList = attendees.getContent();
		
		AttendeeConvertCriteriaDTO convertCriteria = new AttendeeConvertCriteriaDTO();
		List<AttendeeDTO> attendeeDTOs = this.convertAttendeesToAttendeeDTOs(attendeesList,convertCriteria);
		
		AttendeePageDTO attendeePageDTO = new AttendeePageDTO();
		attendeePageDTO.setAttendees(attendeeDTOs);
		attendeePageDTO.setTotalElements(attendees.getTotalElements());
		return ResponseEntity.ok(attendeePageDTO);
	}

	public List<AttendeeDTO> convertAttendeesToAttendeeDTOs(List<Attendee> attendees, AttendeeConvertCriteriaDTO convertCriteria) {
		
		List<AttendeeDTO> attendeeDTOs = new ArrayList<AttendeeDTO>();
		
		for (Attendee attendee : attendees) {
			attendeeDTOs.add(convertAttendeeToAttendeeDTO(attendee,convertCriteria));
		}
		
		return attendeeDTOs;

	}
	
	public AttendeeDTO convertAttendeeToAttendeeDTO(Attendee attendee, AttendeeConvertCriteriaDTO convertCriteria) {
		
		AttendeeDTO attendeeDTO = new AttendeeDTO();
		
		attendeeDTO.setAttendeeId(attendee.getAttendeeId());

	
		attendeeDTO.setFirstName(attendee.getFirstName());

	
		attendeeDTO.setLastName(attendee.getLastName());

	
		attendeeDTO.setEmail(attendee.getEmail());

	

		
		return attendeeDTO;
	}

	public ResultDTO updateAttendee(AttendeeDTO attendeeDTO, RequestDTO requestDTO) {
		
		Attendee attendee = attendeeDao.getById(attendeeDTO.getAttendeeId());

		attendee.setAttendeeId(ControllerUtils.setValue(attendee.getAttendeeId(), attendeeDTO.getAttendeeId()));

		attendee.setFirstName(ControllerUtils.setValue(attendee.getFirstName(), attendeeDTO.getFirstName()));

		attendee.setLastName(ControllerUtils.setValue(attendee.getLastName(), attendeeDTO.getLastName()));

		attendee.setEmail(ControllerUtils.setValue(attendee.getEmail(), attendeeDTO.getEmail()));



        attendee = attendeeDao.save(attendee);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public AttendeeDTO getAttendeeDTOById(Integer attendeeId) {
	
		Attendee attendee = attendeeDao.getById(attendeeId);
			
		
		AttendeeConvertCriteriaDTO convertCriteria = new AttendeeConvertCriteriaDTO();
		return(this.convertAttendeeToAttendeeDTO(attendee,convertCriteria));
	}







}
