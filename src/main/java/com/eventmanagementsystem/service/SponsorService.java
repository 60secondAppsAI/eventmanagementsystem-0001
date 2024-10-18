package com.eventmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.eventmanagementsystem.domain.Sponsor;
import com.eventmanagementsystem.dto.SponsorDTO;
import com.eventmanagementsystem.dto.SponsorSearchDTO;
import com.eventmanagementsystem.dto.SponsorPageDTO;
import com.eventmanagementsystem.dto.SponsorConvertCriteriaDTO;
import com.eventmanagementsystem.service.GenericService;
import com.eventmanagementsystem.dto.common.RequestDTO;
import com.eventmanagementsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SponsorService extends GenericService<Sponsor, Integer> {

	List<Sponsor> findAll();

	ResultDTO addSponsor(SponsorDTO sponsorDTO, RequestDTO requestDTO);

	ResultDTO updateSponsor(SponsorDTO sponsorDTO, RequestDTO requestDTO);

    Page<Sponsor> getAllSponsors(Pageable pageable);

    Page<Sponsor> getAllSponsors(Specification<Sponsor> spec, Pageable pageable);

	ResponseEntity<SponsorPageDTO> getSponsors(SponsorSearchDTO sponsorSearchDTO);
	
	List<SponsorDTO> convertSponsorsToSponsorDTOs(List<Sponsor> sponsors, SponsorConvertCriteriaDTO convertCriteria);

	SponsorDTO getSponsorDTOById(Integer sponsorId);







}





