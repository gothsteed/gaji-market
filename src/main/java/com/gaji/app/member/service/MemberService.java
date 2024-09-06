package com.gaji.app.member.service;

import com.gaji.app.address.domain.Address;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.AddressDTO;
import com.gaji.app.member.dto.MemberDTO;
import com.gaji.app.member.repository.AddressRepository;
import com.gaji.app.member.repository.LikeProductRepository;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.domain.LikeProduct;
import com.gaji.app.product.domain.Product;
import com.gaji.app.product.repository.ProductRepository;
import com.gaji.app.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private AddressRepository addressRepository;
    private PasswordEncoder passwordEncoder;
    private LikeProductRepository likeProductRepository;
    
    @Autowired
    public MemberService(MemberRepository memberRepository, ProductRepository productRepository, ReviewRepository reviewRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, LikeProductRepository likeProductRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.likeProductRepository = likeProductRepository;
    }

    public String emailDuplicateCheck(String email) throws Exception {

        Optional<Member> emailCheck = memberRepository.findByEmail(email);

        return emailCheck.toString();
    }


    public int memberRegister_end(MemberDTO mdto, AddressDTO adto) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // MemberDTO를 Member 엔티티로 변환
        Member member = new Member(mdto.getUserId(), mdto.getName(), mdto.getNickname(), passwordEncoder.encode(mdto.getPassword()), mdto.getEmail(), mdto.getTel(), mdto.getProfilepic());
        try {
            // Member 엔티티 저장
            Member savedMember = memberRepository.save(member);

            Address address = null;
            if(savedMember.getMemberSeq() != null) {
            	
            	address = new Address(adto.getPostcode(), adto.getAddress(), adto.getAddressdetail(), adto.getAddressextra(), savedMember);
            	
            	savedMember.defaultSetAddressseq(address);
            }
            Address savedAddress = addressRepository.save(address);
            
            // 저장된 엔티티의 ID를 반환 (성공적으로 저장되었음을 나타냄)
            return savedAddress.getAddressseq() != null ? 1 : 0;
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
    
    // 수정을 위해 개인정보 가져오기
    public Member getInfo(Long memberSeq) {

        Optional<Member> getInfo = memberRepository.findByMemberSeq(memberSeq);

        return getInfo.orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));
    }

    // 수정시 별명 중복 체크
    public String nicDuplicateCheck(Long memberSeq, String nic) {

        Optional<Member> nicCheck = memberRepository.findByMemberSeqAndNickname(memberSeq, nic);

        return nicCheck.toString();
    }

    // 수정시 비밀번호 중복 체크
    public String pwdDuplicateCheckEdit(Long memberSeq, String pwd){

        pwd = passwordEncoder.encode(pwd);

        // System.out.println("확인용 id : " + id);
        // System.out.println("확인용 pwd : " + pwd);

        Optional<Member> pwdCheck = memberRepository.findByMemberSeqAndPassword(memberSeq, pwd);

        return pwdCheck.toString();
    }

    public String telDuplicateCheckEdit(Long memberSeq, String tel) {

        Optional<Member> pwdCheck = memberRepository.findByMemberSeqAndTel(memberSeq, tel);

        return pwdCheck.toString();
    }


    public String emailDuplicateCheckEdit(Long memberSeq, String email) {

        Optional<Member> pwdCheck = memberRepository.findByMemberSeqAndEmail(memberSeq, email);

        return pwdCheck.toString();
    }


    public int memberEdit_end(Long memberSeq, MemberDTO mdto) {
        try {


            // MemberDTO를 Member 엔티티로 변환
            Member existingMember = memberRepository.findByMemberSeq(memberSeq)
                    .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

            existingMember.UpdateMember(mdto);

            // 업데이트된 엔티티 저장
            memberRepository.save(existingMember);

            // 업데이트 성공 시 1 반환
            return 1;
        } catch (Exception e) {
            // 예외 발생 시 실패를 나타내는 0 반환
            return 0;
        }
    }// end of public int memberEdit_end(MemberDTO mdto)


	public List<Product> getLikedProductsForCurrentUser(Long memberSeq) {
		//  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    // String name = authentication.getName();
	      
	    // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@memberSeq => " + memberSeq);  
		
	      Member member = memberRepository.findByMemberSeq(memberSeq)
	          .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다: " + memberSeq));
	      
	      List<LikeProduct> likedProducts = likeProductRepository.findByMember(member);
	      return likedProducts.stream()
	                          .map(LikeProduct::getProduct)
	                          .collect(Collectors.toList());
	}

}
