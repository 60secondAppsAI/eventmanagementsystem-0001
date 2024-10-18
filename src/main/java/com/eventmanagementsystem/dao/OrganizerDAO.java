package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Organizer;





public interface OrganizerDAO extends GenericDAO<Organizer, Integer> {
  
	List<Organizer> findAll();
	






}


