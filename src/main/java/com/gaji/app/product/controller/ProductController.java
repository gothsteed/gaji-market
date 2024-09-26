package com.gaji.app.product.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.keyword.service.KeywordService;
import com.gaji.app.member.dto.MemberDTO;
import com.gaji.app.member.service.MemberService;
import com.gaji.app.product.domain.Product;
import com.gaji.app.product.domain.ProductImage;
import com.gaji.app.product.dto.CategoryDto;
import com.gaji.app.product.dto.ProductRegistDto;
import com.gaji.app.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    public ProductController(ProductService productService) {
    	this.productService = productService;
    }

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private KeywordService keywordService;

    @GetMapping("productList")
    public String productList(Model model,
                              @RequestParam(defaultValue = "1") int pageNumber,
                              @RequestParam(defaultValue = "productseq") String sortType) {

        try {

            Page<ProductImage> pagingProductList = productService.getProductList(pageNumber, sortType);

            model.addAttribute("productList", pagingProductList.getContent());
            model.addAttribute("currentPage", (pagingProductList.getNumber() + 1)); // 현재 페이지 번호
            model.addAttribute("totalPages", pagingProductList.getTotalPages()); // 총 페이지 수

            // 페이지 번호 리스트 생성
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagingProductList.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

            model.addAttribute("sortType", sortType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "product/productpage";
    }

    @GetMapping("productRegister")
    public String productRegister(Model model) {
    	List<CategoryDto> categories = productService.getAllCategoryInfo();
    	
    	// 현재 로그인된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserDetail userDetail = (MemberUserDetail) authentication.getPrincipal();
        Long memberSeq  = userDetail.getMemberSeq();
    	
    	// 모델에 카테고리 정보를 추가
        model.addAttribute("categories", categories);
        
        // 모델에 로그인 정보를 추가
        model.addAttribute("memberSeq", memberSeq);
        
        return "product/productregister";
    }


    @GetMapping("productDetail")
    public String productDetail(@RequestParam Long seq, Model model) {

        // 현재 로그인된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberUserDetail userDetail = (MemberUserDetail) authentication.getPrincipal();
        Long memberSeq  = userDetail.getMemberSeq();

        // 해당 상품의 정보 가져오기
        Product product = productService.getProductById(seq);
        Long productAuthorId = product.getFkMemberSeq();    // 상품 작성자 seq 가져오기

        // 현재 로그인한 사용자와 상품 작성자가 다를 경우 조회수 증가
        if (memberSeq != null && !memberSeq.equals(productAuthorId)) {
            productService.incrementViewCount(seq);
        }

        // 해당 상품의 모든 이미지 가져오기
        List<ProductImage> images = productService.getProductImgById(seq);

        model.addAttribute("memberSeq", memberSeq);
        model.addAttribute("product", product);
        model.addAttribute("images", images);

        return "product/productdetail";
    }

    @GetMapping("/product/search")
    public String search(@RequestParam String titleSearch, Model model) {
        model.addAttribute("titleSearch", titleSearch);
        model.addAttribute("title", titleSearch + " 검색 결과");
        return "product/productsearch";
    }
    
    @PostMapping("productRegister/end")
    public ModelAndView productRegister_end( @Valid ProductRegistDto prdto, BindingResult bindingResult,  ModelAndView mav, HttpServletRequest request, MultipartHttpServletRequest mtp_request) {
        
    	if (bindingResult.hasErrors()) {
            mav.addObject("errors", bindingResult.getAllErrors());
            mav.setViewName("error");
            return mav;
        }
    	
    	System.out.println("*********컨트롤러까지는 넘어왔다. ");
    	List<MultipartFile> fileList = mtp_request.getFiles("file_arr"); // getFile는 단수 개, getFiles는 List로 반환

		// WAS 의 webapp 의 절대경로 알아오기
		HttpSession session = mtp_request.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root+"resources"+File.separator+"files"+File.separator;
		
		File dir = new File(path);
		if(!dir.exists()) { // community_attach_file 이라는 폴더가 없다면 생성하기
			dir.mkdirs();
		}
		
		String[] arr_attachOrgFilename = null; // 기존 첨부파일명들을 기록하기 위한 용도
		String[] arr_attachNewFilename = null; // 새로운 첨부파일명들을 기록하기 위한 용도
		String[] arr_attachFilesize = null;    // 첨부파일명들의 크기를 기록하기 위한 용도
		
		if(fileList != null && fileList.size() > 0) {
			arr_attachOrgFilename = new String[fileList.size()];
			arr_attachNewFilename = new String[fileList.size()];
			arr_attachFilesize = new String[fileList.size()];
			
			for(int i=0; i<fileList.size(); i++) {
				MultipartFile mtfile = fileList.get(i);
			//	System.out.println("파일명 : " + mtfile.getOriginalFilename() + " / 파일크기 : " + mtfile.getSize());
				/*
					파일명 : berkelekle단가라포인트03.jpg / 파일크기 : 57641
					파일명 : berkelekle덩크04.jpg / 파일크기 : 41931
					파일명 : berkelekle트랜디05.jpg / 파일크기 : 44338
				*/
				String orgFilename = mtfile.getOriginalFilename();
				
				String newFilename = orgFilename.substring(0, orgFilename.lastIndexOf(".")); // 확장자를 뺀 파일명 알아오기
				newFilename += "_" + String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()); // 년월일시분초
    			newFilename += System.nanoTime(); // 나노세컨즈(nanoseconds)
    			newFilename += orgFilename.substring(orgFilename.lastIndexOf(".")); // 확장자 붙이기
		
    			try {
    				File attachFile = new File(path+File.separator+newFilename);
					mtfile.transferTo(attachFile); // 파일을 업로드해주는 것이다.
					
					arr_attachOrgFilename[i] = mtfile.getOriginalFilename(); // 배열 속에 첨부파일명들을 기록한다.
					arr_attachNewFilename[i] = newFilename;
					arr_attachFilesize[i] = Long.toString(mtfile.getSize());
    				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} // end of for ----------
			
		}//end of if(fileList != null && fileList.size() > 0) ------------------
		
		
		// === 첨부 이미지 파일을 저장했으니 그 다음으로 product 정보를 테이블에 insert 해주어야 한다.  ===
		
		// 글번호 채번해오기
		/*Long productSeq = productService.getProductSeq();*/
		//prdto.setProductSeq(productSeq); 
		
		System.out.println("!!!!!!!!!!1111111111111 확인용 : " + prdto.getNegoStatus());
		
		if (prdto.getNegoStatus() == null || "false".equals(prdto.getNegoStatus())) {
		    prdto.setNegoStatus("1"); // 체크 해제 또는 NULL인 경우
		} else if ("true".equals(prdto.getNegoStatus())) {
		    prdto.setNegoStatus("0"); // 체크된 경우
		}
		
		System.out.println("!!!!!!!!!!2222222222222 확인용 : " + prdto.getNegoStatus());
		
		int n1 = productService.productRegister_end(prdto); // product 테이블에 글 내용 insert 결과 
		int n2 = 1; // keyword 인서트 확인
		int n3 = 1; // 첨부파일 insert 성공여부 확인할 변수
		
		if(n1 == 1 && prdto.getKeyword() != null) {
			boolean isSuccess = keywordService.insertKeyword(prdto.getKeyword());
			
			if (isSuccess) { // 성공 처리 (중복이거나 삽입 성공)
		        n2 = 1;
		    } else { // 실패 처리
		    	n2 = 0;
		    }
		}
		
		if(n1 == 1 && fileList != null && fileList.size() > 0) { // 첨부 파일 DB에 저장하기
			int cnt = 0;
			List<ProductImage> productImageList = new ArrayList<>();
			
			for(int i=0; i<fileList.size(); i++) {
				ProductImage productImg = new ProductImage();
				
				productImg.setFkproductseq(prdto.getProductSeq());
				productImg.setOriginalname(arr_attachOrgFilename[i]);
				productImg.setFilename(arr_attachNewFilename[i]);
				
				productImageList.add(productImg);
				
//				Map<String, Object> paraMap = new HashMap<>();
//				
//				paraMap.put("fkproductseq", prdto.getProductSeq()); 
//				paraMap.put("originalname", arr_attachOrgFilename[i]); 
//				paraMap.put("filename", arr_attachNewFilename[i]); 
//			paraMap.put("filesize", arr_attachFilesize[i]); 
			} // end of for ----------	
			
			int attach_update_result = productService.product_attachfile_insert(productImageList);
			
			if(!(attach_update_result == fileList.size())) { // insert 가 성공되어지면 추가 이미지 파일의 갯수가 같아진다. 다른 경우 insert 실패!
        		n3 = 0;
        	}
		}
		
		System.out.println("확인용 n1 : "+ n1);
		System.out.println("확인용 n2 : "+ n2);
		System.out.println("확인용 n3 : "+ n3);
		
		if(n1*n2*n3 == 1) {
			mav.addObject("message", "제품 등록에 성공하였습니다!");
			mav.addObject("loc", "http://localhost:8080/gaji/productList");
			mav.setViewName("msg");
			
		}else {
			mav.addObject("message", "제품등록에 실패하였습니다!");
			mav.addObject("loc", "http://localhost:8080/gaji/productList");
			mav.setViewName("msg");
		}
		
		return mav;
    }
    



}
