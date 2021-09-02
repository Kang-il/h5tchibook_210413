package com.h5tchibook.test.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.test.dao.TestDAO;
import com.h5tchibook.test.model.Test;

@Service
public class TestBO {
	@Autowired
	private TestDAO testDAO;
	
	public Test getTest() {
		return testDAO.selectTest();
	}
}
