package com.h5tchibook.alert.model;

import org.apache.ibatis.type.MappedTypes;

import com.h5tchibook.common.CodeEnum;
import com.h5tchibook.common.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum AlertType implements CodeEnum{
	COMMENT("comment")
	,LIKE("like")
	,FRIEND_REQUEST("friend_request")
	,GROUP_JOIN_REQUEST("group_join_request")
	,GROUP_COMMENT("group_comment")
	,GROUP_LIKE("group_like");
	
	@Getter
	@Setter
	private String alertType;
	
	AlertType(String alertType){
		this.alertType=alertType;
	}
	
	@MappedTypes(AlertType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<AlertType>{
		public TypeHandler() {
			super(AlertType.class);
		}
	}
	
	@Override
	public String getCode() {
		return alertType;
	}
}
