package com.eventmanagementsystem.service;

import com.eventmanagementsystem.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}