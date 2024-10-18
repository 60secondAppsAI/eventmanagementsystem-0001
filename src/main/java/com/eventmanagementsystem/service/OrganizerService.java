package com.eventmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.eventmanagementsystem.domain.Organizer;
import com.eventmanagementsystem.dto.OrganizerDTO;
import com.eventmanagementsystem.dto.OrganizerSearchDTO;
import com.eventmanagementsystem.dto.OrganizerPageDTO;
import com.eventmanagementsystem.dto.OrganizerConvertCriteriaDTO;
import com.eventmanagementsystem.service.GenericService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OrganizerService extends GenericService<Organizer, Integer> {

	List<Organizer> findAll();

	ResultDTO addOrganizer(OrganizerDTO organizerDTO, RequestDTO requestDTO);

	ResultDTO updateOrganizer(OrganizerDTO organizerDTO, RequestDTO requestDTO);

    Page<Organizer> getAllOrganizers(Pageable pageable);

    Page<Organizer> getAllOrganizers(Specification<Organizer> spec, Pageable pageable);

	ResponseEntity<OrganizerPageDTO> getOrganizers(OrganizerSearchDTO organizerSearchDTO);
	
	List<OrganizerDTO> convertOrganizersToOrganizerDTOs(List<Organizer> organizers, OrganizerConvertCriteriaDTO convertCriteria);

	OrganizerDTO getOrganizerDTOById(Integer organizerId);







}





