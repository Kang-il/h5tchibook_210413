package com.h5tchibook.test.dao;

import org.springframework.stereotype.Repository;

import com.h5tchibook.test.model.Test;

@Repository
public interface TestDAO {
	public Test selectTest();
}
