package com.gaji.app.mongo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.mongo.dto.ChatRoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.mongo.dto.ChatRoomWithMessages;
import com.gaji.app.mongo.entity.ChatRoom;
import com.gaji.app.mongo.entity.Message;
import com.gaji.app.mongo.repository.ChatRoomRepository;
import com.gaji.app.mongo.repository.MessageRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ChatService {

    private ChatRoomRepository chatRoomRepository;
    private MemberRepository memberRepository;
    private MessageRepository messageRepository;

	@Autowired
    public ChatService(ChatRoomRepository chatRoomRepository, MemberRepository memberRepository, MessageRepository messageRepository) {
    	this.chatRoomRepository = chatRoomRepository;
    	this.memberRepository = memberRepository;
    	this.messageRepository = messageRepository;
    }

	public ResponseEntity<String> createChatRoom(HttpServletRequest request, HttpServletResponse response,
												 Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq) {

		System.out.println("확인용 샐러 " + sellerMemberSeq);
		System.out.println("확인용 바이어 " + buyerMemberSeq);

		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
		Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

		long sellmemberseq =  sellerChatRoom.get().getMemberSeq();
		System.out.println("확인용 sellmemberseq " + sellmemberseq);
		long buymemberseq =  buyerChatRoom.get().getMemberSeq();
		System.out.println("확인용 buymemberseq " + buymemberseq);

		if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {


			ChatRoom chatRoom = new ChatRoom(sellerMemberSeq.toString(), buyerMemberSeq.toString(), productSeq);
			ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

			if (savedChatRoom.get_id() != null) {
				String roomId = savedChatRoom.get_id();
				return ResponseEntity.status(HttpStatus.OK).body(roomId);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채팅방 생성에 실패했습니다. (roomId가 null입니다.)");
			}
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("채팅방 생성에 실패했습니다.");
	}

	public ModelAndView getChatPage(@AuthenticationPrincipal MemberUserDetail userDetail, HttpServletRequest request, Long sellerMemberSeq, Long buyerMemberSeq,
									String roomId, ModelAndView mav) {

		System.out.println("확인용 샐러 " + sellerMemberSeq);
		System.out.println("확인용 바이어 " + buyerMemberSeq);

		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
	    Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

		long loginUserSeq = userDetail.getMemberSeq();

	    if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {
	       mav.addObject("sellerMemberSeq", sellerChatRoom.get().getMemberSeq());
	       mav.addObject("buyerMemberSeq", buyerChatRoom.get().getMemberSeq());
		   mav.addObject("loginUserSeq", loginUserSeq);
	    } 
		
	    mav.addObject("roomId", roomId); 
	    mav.setViewName("chatting/multichat");
		
		return mav;
	}

	public ResponseEntity<List<ChatRoomWithMessages>> showChatRoom(HttpServletRequest request, HttpServletResponse response, String userId) {
	    
		// 채팅방 리스트 가져오기
	    List<ChatRoom> roomList = chatRoomRepository.findBySellerIdOrBuyerId(userId);

	    // 각 채팅방의 roomId를 사용하여 이전 메시지 가져오기
	    List<ChatRoomWithMessages> chatRoomsWithMessages = new ArrayList<>();
	    for (ChatRoom room : roomList) {
	        List<Message> previousMessages = messageRepository.findAllByRoomId(room.get_id());
	        chatRoomsWithMessages.add(new ChatRoomWithMessages(room, previousMessages));
	    }

	    return ResponseEntity.ok().body(chatRoomsWithMessages);
	}

	public ChatRoomInfo getChatRoomInfo(Long roomId) {

		Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(String.valueOf(roomId));

		if (chatRoomOptional.isPresent()) {
			ChatRoom chatRoom = chatRoomOptional.get();
			Long sellerId = Long.valueOf(chatRoom.getSellerId());
			Long buyerId = Long.valueOf(chatRoom.getBuyerId());

			return new ChatRoomInfo(sellerId, buyerId);
		}

		// 채팅방을 찾지 못한 경우 처리
		return null; // 또는 예외 던지기
	}
}
