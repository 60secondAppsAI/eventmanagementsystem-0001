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
import com.eventmanagementsystem.dao.SessionDAO;
import com.eventmanagementsystem.domain.Session;
import com.eventmanagementsystem.dto.SessionDTO;
import com.eventmanagementsystem.dto.SessionSearchDTO;
import com.eventmanagementsystem.dto.SessionPageDTO;
import com.eventmanagementsystem.dto.SessionConvertCriteriaDTO;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import com.eventmanagementsystem.service.SessionService;
import com.eventmanagementsystem.util.ControllerUtils;





@Service
public class SessionServiceImpl extends GenericServiceImpl<Session, Integer> implements SessionService {

    private final static Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

	@Autowired
	SessionDAO sessionDao;

	


	@Override
	public GenericDAO<Session, Integer> getDAO() {
		return (GenericDAO<Session, Integer>) sessionDao;
	}
	
	public List<Session> findAll () {
		List<Session> sessions = sessionDao.findAll();
		
		return sessions;	
		
	}

	public ResultDTO addSession(SessionDTO sessionDTO, RequestDTO requestDTO) {

		Session session = new Session();

		session.setSessionId(sessionDTO.getSessionId());


		session.setTitle(sessionDTO.getTitle());


		session.setSpeaker(sessionDTO.getSpeaker());


		session.setStartTime(sessionDTO.getStartTime());


		session.setEndTime(sessionDTO.getEndTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		session = sessionDao.save(session);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Session> getAllSessions(Pageable pageable) {
		return sessionDao.findAll(pageable);
	}

	public Page<Session> getAllSessions(Specification<Session> spec, Pageable pageable) {
		return sessionDao.findAll(spec, pageable);
	}

	public ResponseEntity<SessionPageDTO> getSessions(SessionSearchDTO sessionSearchDTO) {
	
			Integer sessionId = sessionSearchDTO.getSessionId(); 
 			String title = sessionSearchDTO.getTitle(); 
 			String speaker = sessionSearchDTO.getSpeaker(); 
   			String sortBy = sessionSearchDTO.getSortBy();
			String sortOrder = sessionSearchDTO.getSortOrder();
			String searchQuery = sessionSearchDTO.getSearchQuery();
			Integer page = sessionSearchDTO.getPage();
			Integer size = sessionSearchDTO.getSize();

	        Specification<Session> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, sessionId, "sessionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, speaker, "speaker"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("speaker")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Session> sessions = this.getAllSessions(spec, pageable);
		
		//System.out.println(String.valueOf(sessions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(sessions.getTotalPages()));
		
		List<Session> sessionsList = sessions.getContent();
		
		SessionConvertCriteriaDTO convertCriteria = new SessionConvertCriteriaDTO();
		List<SessionDTO> sessionDTOs = this.convertSessionsToSessionDTOs(sessionsList,convertCriteria);
		
		SessionPageDTO sessionPageDTO = new SessionPageDTO();
		sessionPageDTO.setSessions(sessionDTOs);
		sessionPageDTO.setTotalElements(sessions.getTotalElements());
		return ResponseEntity.ok(sessionPageDTO);
	}

	public List<SessionDTO> convertSessionsToSessionDTOs(List<Session> sessions, SessionConvertCriteriaDTO convertCriteria) {
		
		List<SessionDTO> sessionDTOs = new ArrayList<SessionDTO>();
		
		for (Session session : sessions) {
			sessionDTOs.add(convertSessionToSessionDTO(session,convertCriteria));
		}
		
		return sessionDTOs;

	}
	
	public SessionDTO convertSessionToSessionDTO(Session session, SessionConvertCriteriaDTO convertCriteria) {
		
		SessionDTO sessionDTO = new SessionDTO();
		
		sessionDTO.setSessionId(session.getSessionId());

	
		sessionDTO.setTitle(session.getTitle());

	
		sessionDTO.setSpeaker(session.getSpeaker());

	
		sessionDTO.setStartTime(session.getStartTime());

	
		sessionDTO.setEndTime(session.getEndTime());

	

		
		return sessionDTO;
	}

	public ResultDTO updateSession(SessionDTO sessionDTO, RequestDTO requestDTO) {
		
		Session session = sessionDao.getById(sessionDTO.getSessionId());

		session.setSessionId(ControllerUtils.setValue(session.getSessionId(), sessionDTO.getSessionId()));

		session.setTitle(ControllerUtils.setValue(session.getTitle(), sessionDTO.getTitle()));

		session.setSpeaker(ControllerUtils.setValue(session.getSpeaker(), sessionDTO.getSpeaker()));

		session.setStartTime(ControllerUtils.setValue(session.getStartTime(), sessionDTO.getStartTime()));

		session.setEndTime(ControllerUtils.setValue(session.getEndTime(), sessionDTO.getEndTime()));



        session = sessionDao.save(session);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SessionDTO getSessionDTOById(Integer sessionId) {
	
		Session session = sessionDao.getById(sessionId);
			
		
		SessionConvertCriteriaDTO convertCriteria = new SessionConvertCriteriaDTO();
		return(this.convertSessionToSessionDTO(session,convertCriteria));
	}







}
