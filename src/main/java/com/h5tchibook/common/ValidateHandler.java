package com.h5tchibook.common;

import java.util.HashMap;		
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.post.model.ValidatePost;

@Component
public class ValidateHandler {
	
	public Map<String,String> validateHandling(Errors errors){
		
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
	
	public String multipartFileValidateHandling(MultipartFile file) {
		String contentType=file.getContentType();
		String originalFileExtension=null;
		
		//확장자 명이 없는 경우 잘못된 파일
		if(ObjectUtils.isEmpty(contentType)) {
			return "valid_empty_extension";
		}else {
			if(contentType.contains("image/jpg")) {
				originalFileExtension="jpg";
			}else if(contentType.contains("image/jpeg")) {
				originalFileExtension="jpeg";
			}else if(contentType.contains("image/png")) {
				originalFileExtension="png";
			}else if(contentType.contains("image/gif")) {
				originalFileExtension="gif";
			}
		}
		
		if(originalFileExtension==null) {
			return "valid_wrong_extension";
		}
		
		return null;
	}
	
	public boolean postValidation(ValidatePost post, MultipartFile file, Errors errors, Map<String,Object> result) {
		boolean validation=false;
		
		if(errors.hasErrors()) {
			Map<String,String> validatorResult=validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				result.put(key, validatorResult.get(key));
				System.out.println("::::::::::::::::::::::::::::::::::"+key);
			}
			result.put("result", false);
			// 유효성 체크 실패시 아래 코드 실행할 필요 없음
			return validation;
		}
		
		//file이 넘어왔을 경우 실행
		if(file!=null) {
			//파일의 확장자를 확인해서 이미지 확장자가아닐경우나 확장자가 없는 파일일 경우 validate 오류 문구를 return
			String validateFile=multipartFileValidateHandling(file);
			
			//오류문구가 반환되었다면 result 맵에 validate문구와 어디서 난 오류인지 담아주고 
			//result 도 false 를 담아주고 바로 리턴해서 아래 코드를 실행하지 않도록한다.
			if(validateFile!=null) {
				result.put(validateFile, "file");
				result.put("result", false);
				return validation;
			}
		}
		
		//아래까지 통과했으면 유효성 체크 이상이 발생하지 않았다.
		validation=true;
		
		return validation;
	}
}
