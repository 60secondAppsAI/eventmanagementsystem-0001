package com.eventmanagementsystem.dao;

import java.util.List;

import com.eventmanagementsystem.dao.GenericDAO;
import com.eventmanagementsystem.domain.Feedback;





public interface FeedbackDAO extends GenericDAO<Feedback, Integer> {
  
	List<Feedback> findAll();
	






}


