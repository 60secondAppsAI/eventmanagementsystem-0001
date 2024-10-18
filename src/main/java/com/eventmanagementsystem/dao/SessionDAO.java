package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Session;





public interface SessionDAO extends GenericDAO<Session, Integer> {
  
	List<Session> findAll();
	






}


