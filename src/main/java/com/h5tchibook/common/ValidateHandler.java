package com.h5tchibook.common;

import java.util.HashMap;	
import java.util.Map;


import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidateHandler {

	public static Map<String,String> validateHandling(Errors errors){
		
		Map<String,String> validatorResult=new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) {//getFieldErrors :: 유효성 검사에 실패한 필드 목록을 가져온다.
			
			//유효성 검사 키 포맷을 정해줌 valid_blahblah~~
			String validKeyName=String.format("valid_%s", error.getField());//getField() :: 실패한 필드명을 가져온다.
			validatorResult.put(validKeyName,error.getDefaultMessage());//getDefaultMessage() :: 유효성 검사에 실패한 필드에 정의된 메시지를 가져온다.
			/*
			 
			  key : valid_{ValidateModel에서 실패한 필드 이름}
			  value : ValidateModel 에서 작성한 message 값
			 
			 */
		}
		
		return validatorResult;
	}
}
