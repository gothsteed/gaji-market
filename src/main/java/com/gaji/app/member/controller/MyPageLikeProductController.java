package com.gaji.app.member.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.LikeProduct;
import com.gaji.app.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyPageLikeProductController {

	  private MemberService memberService;

	    @Autowired
	    public MyPageLikeProductController(MemberService memberService) {
	        this.memberService = memberService;
	    }
	    
	    @GetMapping("/likeproduct")
	    public ModelAndView likeProduct(ModelAndView mav, @AuthenticationPrincipal MemberUserDetail userDetail) {
	        
	    	Long memberSeq = userDetail.getMemberSeq();
	    	 
	    	List<Product> likedProducts = memberService.getLikedProductsForCurrentUser(memberSeq);
	        
	        mav.addObject("likedProducts", likedProducts);
	        mav.setViewName("member/likeproduct");
	        return mav;
	    }
	    
	    @ResponseBody
	    @PostMapping("/likeproduct/delete")
	    public ResponseEntity<Void> removeLike(@RequestParam Long productId, @AuthenticationPrincipal MemberUserDetail userDetail) {
	        
	        Long memberseq = userDetail.getMemberSeq();
	        boolean isDeleted = memberService.removeLike(memberseq, productId);
	        
	        if (isDeleted) {
	        	memberService.decrescLikeCount(productId);
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @ResponseBody
	    @PostMapping("/likeproduct/clicklike")
	    public ResponseEntity<Void> clickLike(@RequestParam Long seq, @AuthenticationPrincipal MemberUserDetail userDetail) {
	    	
	        System.out.println("Request received at: /gaji/likeproduct/clicklike");
	        System.out.println("Request param seq: " + seq);
	    	
	    	Long memberseq = userDetail.getMemberSeq();
	    	boolean isInserted = memberService.clickLike(memberseq, seq);
	    	
	    	if (isInserted) {
	    		memberService.increscLikeCount(seq);
	    		return ResponseEntity.ok().build();
	    	} else {
	    		return ResponseEntity.notFound().build();
	    	}
	    }

}
