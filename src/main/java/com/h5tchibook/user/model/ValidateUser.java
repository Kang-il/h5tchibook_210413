package com.h5tchibook.user.model;

import javax.validation.constraints.NotBlank;	
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //private 필드 값을 가져오는 getter를 자동으로 생성
@Setter //private 필드 값을 설정하는 setter를 자동으로 생성
@NoArgsConstructor //파라미터를 받지 않는 생성자 자동으로 생성
public class ValidateUser {
	@NotBlank(message="blank")
	@Size(min = 4 ,max = 15 ,message="userIdLength")
	private String loginId;
	
	@NotBlank(message="blank")
	@Size(min=2, max=20 , message="nameLength")
	private String name;
	
	@NotBlank(message="blank")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
			,message="passwordValidation")
	private String password;
	
	@NotBlank(message="blank")
	private String sex;

}
