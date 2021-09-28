package com.h5tchibook.alert.dao;

import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.Alert;

@Repository
public interface AlertDAO {
	public void insertAlert(Alert alert);
}
