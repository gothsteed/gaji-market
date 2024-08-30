package com.gaji.app.member.service;

import com.gaji.app.member.dto.MemberDTO;

public interface MemberService {

	String emailDuplicateCheck(String email) throws Exception;

	int memberRegister_end(MemberDTO mdto) throws Exception;

	String idDuplicateCheck(String id) throws Exception;

}
