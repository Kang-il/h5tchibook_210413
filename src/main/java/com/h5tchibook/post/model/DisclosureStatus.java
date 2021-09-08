package com.h5tchibook.post.model;

import org.apache.ibatis.type.MappedTypes;

import com.h5tchibook.common.CodeEnum;
import com.h5tchibook.common.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum DisclosureStatus implements CodeEnum{
	PUBLIC("public"),FRIEND("friend"),PRIVATE("private");
	
	@Getter
	@Setter
	private String disclosureStatus;
	
	DisclosureStatus(String disclousreStatus){
		this.disclosureStatus=disclousreStatus;
	}
	
	@MappedTypes(DisclosureStatus.class)
	public static class TypeHandler extends CodeEnumTypeHandler<DisclosureStatus>{
		public TypeHandler() {
			super(DisclosureStatus.class);
		}
	}
	@Override
	public String getCode() {
		return disclosureStatus;
	}
	

}
