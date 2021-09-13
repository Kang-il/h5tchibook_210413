package com.h5tchibook.user.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class User {
	private int id;
	private String loginId;
	private String password;
	private String name;
	private Sex sex;
	private String profileImagePath;
	private String coverImagePath;
	private String introduce;
	private Date createdAt;
	private Date updatedAt;
}
