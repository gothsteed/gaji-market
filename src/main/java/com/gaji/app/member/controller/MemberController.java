package com.gaji.app.member.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.common.FileManager;
import com.gaji.app.member.dto.MemberDTO;
import com.gaji.app.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
	
	@Autowired
	private FileManager fileManager;
    
    @GetMapping("memberregister")
    public String memberregister() {
        return "member/memberregister";
    }
    
    @ResponseBody
    @PostMapping(value="idDuplicateCheck", produces="text/plain;charset=UTF-8")
    public String idDuplicateCheck(String id) {
    	
    	String idDuplicateCheck = null;
    	try {
    		idDuplicateCheck = memberService.idDuplicateCheck(id);
    		// 회원등록시 입력한 아이디가 이미 있는 아이디인지 검사하는 메소드
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	System.out.println(idDuplicateCheck);
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("idDuplicateCheck", idDuplicateCheck);
    	
    	return jsonObj.toString();
    }
    
    @ResponseBody
	@PostMapping(value="emailDuplicateCheck", produces="text/plain;charset=UTF-8")
	public String emailDuplicateCheck(String email) {
		
		String emailDuplicateCheck = null;
		try {
			emailDuplicateCheck = memberService.emailDuplicateCheck(email);
			// 회원등록시 입력한 이메일이 이미 있는 이메일인지 검사하는 메소드
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(emailDuplicateCheck);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("emailDuplicateCheck", emailDuplicateCheck);
		
		return jsonObj.toString();
	}
    
	@PostMapping(value = "/memberRegisterEnd")
	public ModelAndView memberRegister_end(HttpServletRequest request, ModelAndView mav, MemberDTO mdto, MultipartHttpServletRequest mrequest) {
		
		String tel = request.getParameter("a2") + request.getParameter("hp2") + request.getParameter("hp3"); // 전화번호
		mdto.setTel(tel);
		
		MultipartFile attach =  mdto.getAttach();

		HttpSession session = mrequest.getSession(); 
	    String root = session.getServletContext().getRealPath("/");
	    
	    String path = root+"resources"+File.separator+"files";

	    String newFileName = "";
	    // WAS(톰캣)의 디스크에 저장될 파일명
	     
	    byte[] bytes = null;
	    // 첨부 파일의 내용물을 담은 것
	     
	    try {
			bytes = attach.getBytes();
			// 첨부파일의 내용물을 읽어오는 것
			
			String originalFilename =  attach.getOriginalFilename();
			
			newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
			
			mdto.setProfilepic(newFileName);
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int n = 0;
		try {
			n = memberService.memberRegister_end(mdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(n == 1) {
			mav.addObject("message", "회원가입에 성공하였습니다!");
			mav.addObject("loc", "/home");
			mav.setViewName("msg");
		}
		else {
			mav.addObject("message", "회원가입에 실패하였습니다!");
			mav.addObject("loc", "/home");
			mav.setViewName("msg");
		}
		
		return mav;
	}
}