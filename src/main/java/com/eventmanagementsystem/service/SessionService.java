package com.eventmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.eventmanagementsystem.domain.Session;
import com.eventmanagementsystem.dto.SessionDTO;
import com.eventmanagementsystem.dto.SessionSearchDTO;
import com.eventmanagementsystem.dto.SessionPageDTO;
import com.eventmanagementsystem.dto.SessionConvertCriteriaDTO;
import com.eventmanagementsystem.service.GenericService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SessionService extends GenericService<Session, Integer> {

	List<Session> findAll();

	ResultDTO addSession(SessionDTO sessionDTO, RequestDTO requestDTO);

	ResultDTO updateSession(SessionDTO sessionDTO, RequestDTO requestDTO);

    Page<Session> getAllSessions(Pageable pageable);

    Page<Session> getAllSessions(Specification<Session> spec, Pageable pageable);

	ResponseEntity<SessionPageDTO> getSessions(SessionSearchDTO sessionSearchDTO);
	
	List<SessionDTO> convertSessionsToSessionDTOs(List<Session> sessions, SessionConvertCriteriaDTO convertCriteria);

	SessionDTO getSessionDTOById(Integer sessionId);







}





