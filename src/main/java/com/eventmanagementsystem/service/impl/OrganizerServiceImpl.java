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
import com.eventmanagementsystem.dao.OrganizerDAO;
import com.eventmanagementsystem.domain.Organizer;
import com.eventmanagementsystem.dto.OrganizerDTO;
import com.eventmanagementsystem.dto.OrganizerSearchDTO;
import com.eventmanagementsystem.dto.OrganizerPageDTO;
import com.eventmanagementsystem.dto.OrganizerConvertCriteriaDTO;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import com.eventmanagementsystem.service.OrganizerService;
import com.eventmanagementsystem.util.ControllerUtils;





@Service
public class OrganizerServiceImpl extends GenericServiceImpl<Organizer, Integer> implements OrganizerService {

    private final static Logger logger = LoggerFactory.getLogger(OrganizerServiceImpl.class);

	@Autowired
	OrganizerDAO organizerDao;

	


	@Override
	public GenericDAO<Organizer, Integer> getDAO() {
		return (GenericDAO<Organizer, Integer>) organizerDao;
	}
	
	public List<Organizer> findAll () {
		List<Organizer> organizers = organizerDao.findAll();
		
		return organizers;	
		
	}

	public ResultDTO addOrganizer(OrganizerDTO organizerDTO, RequestDTO requestDTO) {

		Organizer organizer = new Organizer();

		organizer.setOrganizerId(organizerDTO.getOrganizerId());


		organizer.setName(organizerDTO.getName());


		organizer.setContactEmail(organizerDTO.getContactEmail());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		organizer = organizerDao.save(organizer);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Organizer> getAllOrganizers(Pageable pageable) {
		return organizerDao.findAll(pageable);
	}

	public Page<Organizer> getAllOrganizers(Specification<Organizer> spec, Pageable pageable) {
		return organizerDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrganizerPageDTO> getOrganizers(OrganizerSearchDTO organizerSearchDTO) {
	
			Integer organizerId = organizerSearchDTO.getOrganizerId(); 
 			String name = organizerSearchDTO.getName(); 
 			String contactEmail = organizerSearchDTO.getContactEmail(); 
 			String sortBy = organizerSearchDTO.getSortBy();
			String sortOrder = organizerSearchDTO.getSortOrder();
			String searchQuery = organizerSearchDTO.getSearchQuery();
			Integer page = organizerSearchDTO.getPage();
			Integer size = organizerSearchDTO.getSize();

	        Specification<Organizer> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, organizerId, "organizerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, contactEmail, "contactEmail"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("contactEmail")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Organizer> organizers = this.getAllOrganizers(spec, pageable);
		
		//System.out.println(String.valueOf(organizers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(organizers.getTotalPages()));
		
		List<Organizer> organizersList = organizers.getContent();
		
		OrganizerConvertCriteriaDTO convertCriteria = new OrganizerConvertCriteriaDTO();
		List<OrganizerDTO> organizerDTOs = this.convertOrganizersToOrganizerDTOs(organizersList,convertCriteria);
		
		OrganizerPageDTO organizerPageDTO = new OrganizerPageDTO();
		organizerPageDTO.setOrganizers(organizerDTOs);
		organizerPageDTO.setTotalElements(organizers.getTotalElements());
		return ResponseEntity.ok(organizerPageDTO);
	}

	public List<OrganizerDTO> convertOrganizersToOrganizerDTOs(List<Organizer> organizers, OrganizerConvertCriteriaDTO convertCriteria) {
		
		List<OrganizerDTO> organizerDTOs = new ArrayList<OrganizerDTO>();
		
		for (Organizer organizer : organizers) {
			organizerDTOs.add(convertOrganizerToOrganizerDTO(organizer,convertCriteria));
		}
		
		return organizerDTOs;

	}
	
	public OrganizerDTO convertOrganizerToOrganizerDTO(Organizer organizer, OrganizerConvertCriteriaDTO convertCriteria) {
		
		OrganizerDTO organizerDTO = new OrganizerDTO();
		
		organizerDTO.setOrganizerId(organizer.getOrganizerId());

	
		organizerDTO.setName(organizer.getName());

	
		organizerDTO.setContactEmail(organizer.getContactEmail());

	

		
		return organizerDTO;
	}

	public ResultDTO updateOrganizer(OrganizerDTO organizerDTO, RequestDTO requestDTO) {
		
		Organizer organizer = organizerDao.getById(organizerDTO.getOrganizerId());

		organizer.setOrganizerId(ControllerUtils.setValue(organizer.getOrganizerId(), organizerDTO.getOrganizerId()));

		organizer.setName(ControllerUtils.setValue(organizer.getName(), organizerDTO.getName()));

		organizer.setContactEmail(ControllerUtils.setValue(organizer.getContactEmail(), organizerDTO.getContactEmail()));



        organizer = organizerDao.save(organizer);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrganizerDTO getOrganizerDTOById(Integer organizerId) {
	
		Organizer organizer = organizerDao.getById(organizerId);
			
		
		OrganizerConvertCriteriaDTO convertCriteria = new OrganizerConvertCriteriaDTO();
		return(this.convertOrganizerToOrganizerDTO(organizer,convertCriteria));
	}







}
