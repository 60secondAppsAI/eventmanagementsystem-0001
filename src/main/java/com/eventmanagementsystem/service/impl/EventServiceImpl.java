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
import com.eventmanagementsystem.dao.EventDAO;
import com.eventmanagementsystem.domain.Event;
import com.eventmanagementsystem.dto.EventDTO;
import com.eventmanagementsystem.dto.EventSearchDTO;
import com.eventmanagementsystem.dto.EventPageDTO;
import com.eventmanagementsystem.dto.EventConvertCriteriaDTO;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import com.eventmanagementsystem.service.EventService;
import com.eventmanagementsystem.util.ControllerUtils;





@Service
public class EventServiceImpl extends GenericServiceImpl<Event, Integer> implements EventService {

    private final static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	EventDAO eventDao;

	


	@Override
	public GenericDAO<Event, Integer> getDAO() {
		return (GenericDAO<Event, Integer>) eventDao;
	}
	
	public List<Event> findAll () {
		List<Event> events = eventDao.findAll();
		
		return events;	
		
	}

	public ResultDTO addEvent(EventDTO eventDTO, RequestDTO requestDTO) {

		Event event = new Event();

		event.setEventId(eventDTO.getEventId());


		event.setName(eventDTO.getName());


		event.setDate(eventDTO.getDate());


		event.setLocation(eventDTO.getLocation());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		event = eventDao.save(event);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Event> getAllEvents(Pageable pageable) {
		return eventDao.findAll(pageable);
	}

	public Page<Event> getAllEvents(Specification<Event> spec, Pageable pageable) {
		return eventDao.findAll(spec, pageable);
	}

	public ResponseEntity<EventPageDTO> getEvents(EventSearchDTO eventSearchDTO) {
	
			Integer eventId = eventSearchDTO.getEventId(); 
 			String name = eventSearchDTO.getName(); 
   			String location = eventSearchDTO.getLocation(); 
 			String sortBy = eventSearchDTO.getSortBy();
			String sortOrder = eventSearchDTO.getSortOrder();
			String searchQuery = eventSearchDTO.getSearchQuery();
			Integer page = eventSearchDTO.getPage();
			Integer size = eventSearchDTO.getSize();

	        Specification<Event> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, eventId, "eventId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, location, "location"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("location")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Event> events = this.getAllEvents(spec, pageable);
		
		//System.out.println(String.valueOf(events.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(events.getTotalPages()));
		
		List<Event> eventsList = events.getContent();
		
		EventConvertCriteriaDTO convertCriteria = new EventConvertCriteriaDTO();
		List<EventDTO> eventDTOs = this.convertEventsToEventDTOs(eventsList,convertCriteria);
		
		EventPageDTO eventPageDTO = new EventPageDTO();
		eventPageDTO.setEvents(eventDTOs);
		eventPageDTO.setTotalElements(events.getTotalElements());
		return ResponseEntity.ok(eventPageDTO);
	}

	public List<EventDTO> convertEventsToEventDTOs(List<Event> events, EventConvertCriteriaDTO convertCriteria) {
		
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		
		for (Event event : events) {
			eventDTOs.add(convertEventToEventDTO(event,convertCriteria));
		}
		
		return eventDTOs;

	}
	
	public EventDTO convertEventToEventDTO(Event event, EventConvertCriteriaDTO convertCriteria) {
		
		EventDTO eventDTO = new EventDTO();
		
		eventDTO.setEventId(event.getEventId());

	
		eventDTO.setName(event.getName());

	
		eventDTO.setDate(event.getDate());

	
		eventDTO.setLocation(event.getLocation());

	

		
		return eventDTO;
	}

	public ResultDTO updateEvent(EventDTO eventDTO, RequestDTO requestDTO) {
		
		Event event = eventDao.getById(eventDTO.getEventId());

		event.setEventId(ControllerUtils.setValue(event.getEventId(), eventDTO.getEventId()));

		event.setName(ControllerUtils.setValue(event.getName(), eventDTO.getName()));

		event.setDate(ControllerUtils.setValue(event.getDate(), eventDTO.getDate()));

		event.setLocation(ControllerUtils.setValue(event.getLocation(), eventDTO.getLocation()));



        event = eventDao.save(event);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EventDTO getEventDTOById(Integer eventId) {
	
		Event event = eventDao.getById(eventId);
			
		
		EventConvertCriteriaDTO convertCriteria = new EventConvertCriteriaDTO();
		return(this.convertEventToEventDTO(event,convertCriteria));
	}







}
