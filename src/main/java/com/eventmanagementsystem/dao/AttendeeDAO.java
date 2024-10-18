package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Attendee;





public interface AttendeeDAO extends GenericDAO<Attendee, Integer> {
  
	List<Attendee> findAll();
	






}


