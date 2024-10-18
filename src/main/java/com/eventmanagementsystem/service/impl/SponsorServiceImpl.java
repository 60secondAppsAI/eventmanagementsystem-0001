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
import com.eventmanagementsystem.dao.SponsorDAO;
import com.eventmanagementsystem.domain.Sponsor;
import com.eventmanagementsystem.dto.SponsorDTO;
import com.eventmanagementsystem.dto.SponsorSearchDTO;
import com.eventmanagementsystem.dto.SponsorPageDTO;
import com.eventmanagementsystem.dto.SponsorConvertCriteriaDTO;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import com.eventmanagementsystem.service.SponsorService;
import com.eventmanagementsystem.util.ControllerUtils;





@Service
public class SponsorServiceImpl extends GenericServiceImpl<Sponsor, Integer> implements SponsorService {

    private final static Logger logger = LoggerFactory.getLogger(SponsorServiceImpl.class);

	@Autowired
	SponsorDAO sponsorDao;

	


	@Override
	public GenericDAO<Sponsor, Integer> getDAO() {
		return (GenericDAO<Sponsor, Integer>) sponsorDao;
	}
	
	public List<Sponsor> findAll () {
		List<Sponsor> sponsors = sponsorDao.findAll();
		
		return sponsors;	
		
	}

	public ResultDTO addSponsor(SponsorDTO sponsorDTO, RequestDTO requestDTO) {

		Sponsor sponsor = new Sponsor();

		sponsor.setSponsorId(sponsorDTO.getSponsorId());


		sponsor.setName(sponsorDTO.getName());


		sponsor.setContribution(sponsorDTO.getContribution());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		sponsor = sponsorDao.save(sponsor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Sponsor> getAllSponsors(Pageable pageable) {
		return sponsorDao.findAll(pageable);
	}

	public Page<Sponsor> getAllSponsors(Specification<Sponsor> spec, Pageable pageable) {
		return sponsorDao.findAll(spec, pageable);
	}

	public ResponseEntity<SponsorPageDTO> getSponsors(SponsorSearchDTO sponsorSearchDTO) {
	
			Integer sponsorId = sponsorSearchDTO.getSponsorId(); 
 			String name = sponsorSearchDTO.getName(); 
 			String contribution = sponsorSearchDTO.getContribution(); 
 			String sortBy = sponsorSearchDTO.getSortBy();
			String sortOrder = sponsorSearchDTO.getSortOrder();
			String searchQuery = sponsorSearchDTO.getSearchQuery();
			Integer page = sponsorSearchDTO.getPage();
			Integer size = sponsorSearchDTO.getSize();

	        Specification<Sponsor> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, sponsorId, "sponsorId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, contribution, "contribution"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("contribution")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Sponsor> sponsors = this.getAllSponsors(spec, pageable);
		
		//System.out.println(String.valueOf(sponsors.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(sponsors.getTotalPages()));
		
		List<Sponsor> sponsorsList = sponsors.getContent();
		
		SponsorConvertCriteriaDTO convertCriteria = new SponsorConvertCriteriaDTO();
		List<SponsorDTO> sponsorDTOs = this.convertSponsorsToSponsorDTOs(sponsorsList,convertCriteria);
		
		SponsorPageDTO sponsorPageDTO = new SponsorPageDTO();
		sponsorPageDTO.setSponsors(sponsorDTOs);
		sponsorPageDTO.setTotalElements(sponsors.getTotalElements());
		return ResponseEntity.ok(sponsorPageDTO);
	}

	public List<SponsorDTO> convertSponsorsToSponsorDTOs(List<Sponsor> sponsors, SponsorConvertCriteriaDTO convertCriteria) {
		
		List<SponsorDTO> sponsorDTOs = new ArrayList<SponsorDTO>();
		
		for (Sponsor sponsor : sponsors) {
			sponsorDTOs.add(convertSponsorToSponsorDTO(sponsor,convertCriteria));
		}
		
		return sponsorDTOs;

	}
	
	public SponsorDTO convertSponsorToSponsorDTO(Sponsor sponsor, SponsorConvertCriteriaDTO convertCriteria) {
		
		SponsorDTO sponsorDTO = new SponsorDTO();
		
		sponsorDTO.setSponsorId(sponsor.getSponsorId());

	
		sponsorDTO.setName(sponsor.getName());

	
		sponsorDTO.setContribution(sponsor.getContribution());

	

		
		return sponsorDTO;
	}

	public ResultDTO updateSponsor(SponsorDTO sponsorDTO, RequestDTO requestDTO) {
		
		Sponsor sponsor = sponsorDao.getById(sponsorDTO.getSponsorId());

		sponsor.setSponsorId(ControllerUtils.setValue(sponsor.getSponsorId(), sponsorDTO.getSponsorId()));

		sponsor.setName(ControllerUtils.setValue(sponsor.getName(), sponsorDTO.getName()));

		sponsor.setContribution(ControllerUtils.setValue(sponsor.getContribution(), sponsorDTO.getContribution()));



        sponsor = sponsorDao.save(sponsor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SponsorDTO getSponsorDTOById(Integer sponsorId) {
	
		Sponsor sponsor = sponsorDao.getById(sponsorId);
			
		
		SponsorConvertCriteriaDTO convertCriteria = new SponsorConvertCriteriaDTO();
		return(this.convertSponsorToSponsorDTO(sponsor,convertCriteria));
	}







}
