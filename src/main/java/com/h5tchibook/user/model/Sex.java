package com.h5tchibook.user.model;

import org.apache.ibatis.type.MappedTypes;

import com.h5tchibook.common.CodeEnum;
import com.h5tchibook.common.CodeEnumTypeHandler;

import lombok.Getter;

public enum Sex implements CodeEnum{
	MALE("male"), FEMALE("female");
	
	@Getter
	private String sex;
	
	Sex(String sex){
		this.sex=sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	//Enum 클래스 내부에 TypeHandler 과 같은 static 클래스를 정의
	//CodeEnumTypeHandler 상속하여 공통적인 비즈니스 로직을 처리하도록 함.
	//Java Config 로 myBatis 의 TypeHandler 을 설정하기 위해 
	//@MappedTypes(클래스) 와 같이 명시적으로 정의해 주어야 한다.
	@MappedTypes(Sex.class)
	public static class TypeHandler extends CodeEnumTypeHandler<Sex>{
		public TypeHandler() {
			//Enum클래스를 넣어줌
			super(Sex.class);
		}
	}
	
	@Override
	public String getCode() {
		return sex;
	}

}
