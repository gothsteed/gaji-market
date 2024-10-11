package com.gaji.app.mongo.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.mongo.dto.MessageDTO;
import com.gaji.app.mongo.entity.Message;
import com.gaji.app.mongo.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())  // Java 8 날짜/시간 모듈 등록
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // 타임스탬프로 쓰는 대신 ISO-8601 형식으로 날짜 기록

    // 채팅방 ID별로 연결된 세션들을 관리
    private final Map<String, List<WebSocketSession>> roomSessions = new HashMap<>();

    @Autowired
    public ChatWebSocketHandler(MessageRepository messageRepository, MemberRepository memberRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        Long loginUserSeq = (Long) session.getAttributes().get("loginuser");

        Optional<Member> loginuser = memberRepository.findByMemberSeq(loginUserSeq);
        String loginUserNic = loginuser.map(Member::getNickname).orElse("Unknown User");
        String role = determineUserRole(session, loginUserSeq);

        System.out.println("확인용 roomId " + roomId);
        System.out.println("확인용 loginUserSeq " + loginUserSeq);
        System.out.println("확인용 role " + role);

        // 채팅방에 세션 추가
        roomSessions.computeIfAbsent(roomId, k -> new ArrayList<>()).add(session);
        role = role.equals("SELLER") ? "판매자" : "구매자";
        // 입장 메시지 전송
        Message enterMessage = new Message(
                String.valueOf(loginUserSeq),
                loginUserNic,
                role,
                loginUserNic + " 님이 입장했습니다 (" + role + ")",
                roomId,
                LocalDateTime.now()
        );

        // 이전 메시지 불러오기 및 전송
        sendPreviousMessages(session, roomId);

        // 입장 메시지 채팅방 전체 전송
        sendToAllInRoom(roomId, enterMessage);

        // 메시지 저장
        messageRepository.save(enterMessage);
    }

    private String determineUserRole(WebSocketSession session, Long loginUserSeq) {
        String sellerMemberSeq = (String) session.getAttributes().get("sellerMemberSeq");
        String buyerMemberSeq = (String) session.getAttributes().get("buyerMemberSeq");

        if (loginUserSeq.toString().equals(sellerMemberSeq)) {
            return "SELLER";
        } else if (loginUserSeq.toString().equals(buyerMemberSeq)) {
            return "BUYER";
        } else {
            return "UNKNOWN";
        }
    }

    // 이전 메시지 전송
    private void sendPreviousMessages(WebSocketSession session, String roomId) throws IOException {
        List<Message> previousMessages = messageRepository.findAllByRoomId(roomId);
        for (Message message : previousMessages) {

            String seq = message.getSenderSeq();
            String nickname = message.getSenderNickname();
            String role = message.getSenderType();
            String content = message.getContent();
            LocalDateTime currentTime = message.getCurrentTime();

            Message previousMessage = new Message(
                    seq,
                    nickname,
                    role,
                    content,
                    roomId,
                    currentTime
            );

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(previousMessage)));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        Long loginUserSeq = (Long) session.getAttributes().get("loginuser");
        String role = (String) session.getAttributes().get("role");

        // 클라이언트에서 받은 메시지 처리 (MessageDTO 사용)
        MessageDTO messageDto = MessageDTO.convertMessage(textMessage.getPayload());

        // 새로운 메시지 생성
        Message newMessage = new Message(
                loginUserSeq.toString(),
                messageDto.getSenderNickname(),
                role,
                messageDto.getContent(),
                roomId,
                LocalDateTime.now()
        );

        // 메시지 저장
        messageRepository.save(newMessage);

        // 모든 클라이언트에게 메시지 전송
        sendToAllInRoom(roomId, newMessage);
    }

    private void sendToAllInRoom(String roomId, Message message) throws IOException {
        for (WebSocketSession connectedSession : roomSessions.get(roomId)) {
            connectedSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        Long loginUserSeq = (Long) session.getAttributes().get("loginuser");
        String role = (String) session.getAttributes().get("role");

        Optional<Member> loginuser = memberRepository.findByMemberSeq(loginUserSeq);
        String loginUserNic = loginuser.get().getNickname();

        role = role.equals("SELLER") ? "판매자" : "구매자";

        // 퇴장 메시지 생성
        Message exitMessage = new Message(
                loginUserSeq.toString(),
                loginUserNic,
                role,
                loginUserNic + " 님이 퇴장했습니다 (" + role + ")",
                roomId,
                LocalDateTime.now()
        );

        // 채팅방에서 세션 제거
        List<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(roomId);
            }
        }

        // 퇴장 메시지 채팅방 전체 전송
        for (WebSocketSession connectedSession : roomSessions.get(roomId)) {
            connectedSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(exitMessage)));
        }

        // 메시지 저장
        messageRepository.save(exitMessage);
    }
}