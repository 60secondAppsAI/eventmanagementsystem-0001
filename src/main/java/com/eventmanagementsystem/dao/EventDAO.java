package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Event;





public interface EventDAO extends GenericDAO<Event, Integer> {
  
	List<Event> findAll();
	






}


