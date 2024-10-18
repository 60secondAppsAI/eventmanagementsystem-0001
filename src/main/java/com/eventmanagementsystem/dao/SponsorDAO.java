package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Sponsor;





public interface SponsorDAO extends GenericDAO<Sponsor, Integer> {
  
	List<Sponsor> findAll();
	






}


