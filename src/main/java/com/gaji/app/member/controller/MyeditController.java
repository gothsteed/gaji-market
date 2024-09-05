package com.gaji.app.member.controller;

import com.gaji.app.common.FileManager;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.MemberDTO;
import com.gaji.app.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
public class MyeditController {

    private MemberService memberService;
    private FileManager fileManager;


    @Autowired
    public MyeditController(MemberService memberService, FileManager fileManager) {
        this.memberService = memberService;
        this.fileManager = fileManager;
    }

    @GetMapping("/myedit")
    public ModelAndView myedit(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mav, Member member) {

        String userId = userDetails.getUsername();
        // System.out.println("확인용 이름 : " + userId);

        member = memberService.getInfo(userId);



        mav.addObject("member", member);
        mav.setViewName("member/myedit");
        return mav;
    }

    @ResponseBody
    @PostMapping(value="/myedit/nicDuplicateCheck", produces="text/plain;charset=UTF-8")
    public String nicDuplicateCheck (@AuthenticationPrincipal UserDetails userDetails, String nic){

        String nicDuplicateCheck = null;
        String id = userDetails.getUsername();

        try {
            nicDuplicateCheck = memberService.nicDuplicateCheck(id, nic);
        }catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("nicDuplicateCheck", nicDuplicateCheck);

        return jsonObj.toString();
    }

    @ResponseBody
    @PostMapping(value="/myedit/pwdDuplicateCheck", produces="text/plain;charset=UTF-8")
    public String pwdDuplicateCheck (@AuthenticationPrincipal UserDetails userDetails, String pwd){

        String pwdDuplicateCheck = null;
        String id = userDetails.getUsername();

        try {
            pwdDuplicateCheck = memberService.pwdDuplicateCheckEdit(id, pwd);
        }catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("pwdDuplicateCheck", pwdDuplicateCheck);

        return jsonObj.toString();
    }

    // 연락처 중복체크
    @ResponseBody
    @PostMapping(value="/myedit/telDuplicateCheck", produces="text/plain;charset=UTF-8")
    public String telDuplicateCheck (@AuthenticationPrincipal UserDetails userDetails, String tel){

        String telDuplicateCheck = null;
        String id = userDetails.getUsername();

        try {
            telDuplicateCheck = memberService.telDuplicateCheckEdit(id, tel);
        }catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("telDuplicateCheck", telDuplicateCheck);

        return jsonObj.toString();
    }

    // 이메일 중복체크
    @ResponseBody
    @PostMapping(value="/myedit/emailDuplicateCheck", produces="text/plain;charset=UTF-8")
    public String emailDuplicateCheck (@AuthenticationPrincipal UserDetails userDetails, String email){

        String emailDuplicateCheck = null;
        String id = userDetails.getUsername();

        try {
            emailDuplicateCheck = memberService.emailDuplicateCheckEdit(id, email);
        }catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("emailDuplicateCheck", emailDuplicateCheck);

        return jsonObj.toString();
    }

    @PostMapping(value="/myedit/editEnd")
    public ModelAndView editEnd(@AuthenticationPrincipal UserDetails userDetails, ModelAndView mav, MemberDTO mdto, MultipartHttpServletRequest mrequest, HttpServletRequest request){

        String id = userDetails.getUsername();
        String nic = request.getParameter("nic");
        String pwd = request.getParameter("pwd");
        String tel = request.getParameter("tel");
        String email = request.getParameter("email");

        mdto.setNickname(nic);
        mdto.setPassword(pwd);
        mdto.setTel(tel);
        mdto.setEmail(email);

        MultipartFile attach =  mdto.getAttach();

        HttpSession session = mrequest.getSession();
        String root = session.getServletContext().getRealPath("/");

        String path = root+"resources"+ File.separator+"files";

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
            n = memberService.memberEdit_end(mdto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(n == 1) {
            mav.addObject("message", "정보수정에 성공하였습니다!");
            mav.addObject("loc", "http://localhost:8080/gaji/myPage");
            mav.setViewName("msg");
			/*
			System.out.println("Message: " + mav.getModel().get("message"));
			System.out.println("Location: " + mav.getModel().get("loc"));
			*/
        }
        else {
            mav.addObject("message", "정보수정에 실패하였습니다!");
            mav.addObject("loc", "http://localhost:8080/gaji/myedit");
            mav.setViewName("msg");
        }

        return mav;
    }


}
