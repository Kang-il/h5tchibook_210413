package com.h5tchibook.post.model;

import org.apache.ibatis.type.MappedTypes;

import com.h5tchibook.common.CodeEnum;
import com.h5tchibook.common.CodeEnumTypeHandler;

import lombok.Getter;
import lombok.Setter;

public enum ContentType implements CodeEnum{
	TEXT("text"),PHOTO("photo"),VIDEO("video");
	
	@Getter
	@Setter
	private String contentType;
	
	ContentType(String contentType) {
		this.contentType =contentType;
	}
	
	@MappedTypes(ContentType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<ContentType>{
		public TypeHandler() {
			super(ContentType.class);
		}
	}
	@Override
	public String getCode() {
		return contentType;
	}
	
}
