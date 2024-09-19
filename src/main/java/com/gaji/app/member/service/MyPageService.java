package com.gaji.app.member.service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.repository.ProductRepository;
import com.gaji.app.reservation.domain.Reservation;
import com.gaji.app.reservation.repository.ReservationRepository;
import com.gaji.app.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    private MemberRepository memberRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private ReservationRepository reservationRepository;

    public MyPageService(MemberRepository memberRepository, ProductRepository productRepository,
                         ReviewRepository reviewRepository, ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
    }

    public MyPageDto getMyPageInfo(Long userid) {
        Member member = memberRepository.findById(userid).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디 입니다: " + userid));
        int productCount = productRepository.countOnSaleProductsByMemberSeq(userid);
        int reviewCount = reviewRepository.countReviewByUserid(userid);
        int likeCount = productRepository.countLikedProductByUserid(userid);
        int soldCount = productRepository.countSoldProductsByMemberSeq(userid);
        int reservationCount = reservationRepository.countByFkmemberseq(userid);

        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setName(member.getName());
        myPageDto.setMannerTemp(member.getMannerTemp());
        myPageDto.setOnSaleCount(productCount);
        myPageDto.setSoldCount(soldCount);
        myPageDto.setReservationCount(reservationCount);
        myPageDto.setReviewCount(reviewCount);
        myPageDto.setLikedProductCount(likeCount);
        myPageDto.setAddress(member.getAddress().getAddress());

        return myPageDto;
    }

    // 구매 예약 목록
    public Page<Reservation> getReservationList(Long memberSeq, int pageNumber) throws Exception {

        int pageSize = 5;
        int start = (pageNumber - 1) * pageSize + 1; // 시작 인덱스
        int end = start + pageSize - 1; // 종료 인덱스

        // 페이징된 결과 가져오기
        List<Reservation> pagingReservationList = reservationRepository.findAllByFkmemberseq(memberSeq, start, end);

        // 전체 데이터 수 가져오기
        long totalCount = reservationRepository.countByFkmemberseq(memberSeq);

        return new PageImpl<>(pagingReservationList, PageRequest.of(pageNumber - 1, pageSize), totalCount);
    }
}
