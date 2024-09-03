package com.gaji.app.member.service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.MemberDTO;
import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.repository.ProductRepository;
import com.gaji.app.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public MyPageDto getMyPageInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();

        Member member = memberRepository.findByUserId(userid).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디 입니다: " + userid));
        int productCount = productRepository.countOnSaleProductsByMemberSeq(userid);
        int reviewCount = reviewRepository.countReviewByUserid(userid);
        int likeCount = productRepository.countLikedProductByUserid(userid);
        int soldCount = productRepository.countSoldProductsByMemberSeq(userid);

        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setName(member.getName());
        myPageDto.setMannerTemp(member.getMannerTemp());
        myPageDto.setOnSaleCount(productCount);
        myPageDto.setSoldCount(soldCount);
        myPageDto.setReviewCount(reviewCount);
        myPageDto.setLikedProductCount(likeCount);


        return myPageDto;
    }


    public String emailDuplicateCheck(String email) throws Exception {

        Optional<Member> emailCheck = memberRepository.findByEmail(email);

        return emailCheck.toString();
    }


    public int memberRegister_end(MemberDTO mdto) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // MemberDTO를 Member 엔티티로 변환
        Member member = new Member(mdto.getUserId(), mdto.getName(), mdto.getNickname(), passwordEncoder.encode(mdto.getPassword()), mdto.getEmail(), mdto.getTel(), mdto.getProfilepic());

        try {
            // Member 엔티티 저장
            Member savedMember = memberRepository.save(member);

            // 저장된 엔티티의 ID를 반환 (성공적으로 저장되었음을 나타냄)
            return savedMember.getMemberSeq() != null ? 1 : 0;
        } catch (Exception e) {
            // 예외가 발생하면 실패를 나타내는 0을 반환
            e.printStackTrace();
            return 0;
        }
    }

    public String idDuplicateCheck(String id) throws Exception {

        Optional<Member> idCheck = memberRepository.findByUserId(id);

        return idCheck.toString();
    }
}
