package com.gaji.app.member.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	
	private String userId;
	private String name;
	private String nickname;
	private String password;
	private String tel;
	private String email;
	private String profilepic;
	
	private MultipartFile attach;
	
	
}
