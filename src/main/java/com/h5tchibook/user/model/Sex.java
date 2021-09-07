package com.h5tchibook.user.model;

import lombok.Getter;

public enum Sex {
	MALE("male"), FEMALE("female");
	
	@Getter
	private String sex;
	
	Sex(String sex){
		this.sex=sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
}
