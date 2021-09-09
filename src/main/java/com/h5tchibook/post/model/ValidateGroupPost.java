package com.h5tchibook.post.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class ValidateGroupPost implements Post{
	@NotBlank(message="blank")
	private String content;

	@NotBlank(message="blank")
	private Integer groupId;
}
