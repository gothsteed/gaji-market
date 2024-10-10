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

	public ResponseEntity<String> findChatRoom(Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq) {
		
		// 채팅방을 찾기 위한 조건 설정
	    List<ChatRoom> chatRooms = chatRoomRepository.findBySellerMemberSeqAndBuyerMemberSeqAndProductSeq(sellerMemberSeq, buyerMemberSeq, productSeq);

	    // 채팅방이 존재하는지 확인
	    if (!chatRooms.isEmpty()) {
	        // 첫 번째 채팅방의 ID를 반환
	        String roomId = chatRooms.get(0).get_id();
	        return ResponseEntity.ok(roomId);
	    }

		// 구매자가 채팅하기 눌렀을 때
		if(chatRooms.isEmpty()){
			chatRooms = chatRoomRepository.findBySellerMemberSeqOrBuyerMemberSeq(buyerMemberSeq);
			String roomId = chatRooms.get(0).get_id();
			System.out.println("확인용 roomId " + roomId);
			return ResponseEntity.ok(roomId);

		}

	    // 채팅방이 없으면 null 반환
	    return ResponseEntity.ok(null);
	}
	
	public ResponseEntity<String> createChatRoom(HttpServletRequest request, HttpServletResponse response,
												 Long sellerMemberSeq, Long buyerMemberSeq, Long productSeq) {

		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
		Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

		if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {

			ChatRoom chatRoom = new ChatRoom(sellerMemberSeq, buyerMemberSeq, productSeq);
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

		Optional<Member> sellerChatRoom = memberRepository.findByMemberSeq(sellerMemberSeq);
	    Optional<Member> buyerChatRoom = memberRepository.findByMemberSeq(buyerMemberSeq);

		long loginUserSeq = userDetail.getMemberSeq();
		Optional<Member> loginUser = memberRepository.findByMemberSeq(loginUserSeq);
		String loginUserNickname = loginUser.get().getNickname();
		
	    if (sellerChatRoom.isPresent() && buyerChatRoom.isPresent()) {
	       mav.addObject("sellerMemberSeq", sellerChatRoom.get().getMemberSeq());
	       mav.addObject("buyerMemberSeq", buyerChatRoom.get().getMemberSeq());
		   mav.addObject("loginUserSeq", loginUserSeq);
		   mav.addObject("loginUserNickname", loginUserNickname);
	    } 
		
	    mav.addObject("roomId", roomId); 
	    mav.setViewName("chatting/multichat");
		
		return mav;
	}

	public ResponseEntity<List<ChatRoomWithMessages>> showChatRoom(HttpServletRequest request, HttpServletResponse response, Long memberSeq) {
	    
		// 채팅방 리스트 가져오기
	    List<ChatRoom> roomList = chatRoomRepository.findBySellerMemberSeqOrBuyerMemberSeq(memberSeq);
	    
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
			Long sellerId = chatRoom.getSellerMemberSeq();
			Long buyerId = chatRoom.getBuyerMemberSeq();

			return new ChatRoomInfo(sellerId, buyerId);
		}

		// 채팅방을 찾지 못한 경우 처리
		return null; // 또는 예외 던지기
	}
}
