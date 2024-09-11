package com.gaji.app.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberEditDTO {

	private String userId;
	private String nickname;
	private String password;
	private String tel;
	private String email;
	private String profilepic;
	
	private MultipartFile attach;
	
	
}
