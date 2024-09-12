package com.gaji.app.mongo.controller;

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
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환을 위해 ObjectMapper 사용

    // 채팅방 ID별로 연결된 세션들을 관리
    private final Map<String, List<WebSocketSession>> roomSessions = new HashMap<>();

    @Autowired
    public ChatWebSocketHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 이전 메시지 전송
    private void sendPreviousMessages(WebSocketSession session, List<Message> previousMessages) throws IOException {
        for (Message message : previousMessages) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        Long sellerMemberSeq = Long.parseLong((String) session.getAttributes().get("SellerMemberSeq"));
        Long buyerMemberSeq = Long.parseLong((String) session.getAttributes().get("BuyerMemberSeq"));
        Long loginUserSeq = (Long) session.getAttributes().get("loginuser");

        // 채팅방에 세션 추가
        roomSessions.computeIfAbsent(roomId, k -> new ArrayList<>());
        roomSessions.get(roomId).add(session);

        // 사용자 역할 구분
        String role;
        if (loginUserSeq.equals(sellerMemberSeq)) {
            role = "SELLER";
        } else if (loginUserSeq.equals(buyerMemberSeq)) {
            role = "BUYER";
        } else {
            role = "UNKNOWN";
        }

        // 사용자 역할 저장
        session.getAttributes().put("role", role);

        // 입장 메시지 전송
        Message enterMessage = new Message(
                loginUserSeq.toString(),
                role.equals("SELLER") ? "판매자" : "구매자",
                role,
                loginUserSeq + " 님이 입장했습니다 (" + role + ")",
                roomId,
                LocalDateTime.now()
        );

        // 이전 메시지 불러오기 및 전송
        List<Message> previousMessages = messageRepository.findAllByRoomId(roomId);
        sendPreviousMessages(session, previousMessages);

        // 입장 메시지 채팅방 전체 전송
        for (WebSocketSession connectedSession : roomSessions.get(roomId)) {
            connectedSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(enterMessage)));
        }

        // 메시지 저장
        messageRepository.save(enterMessage);
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
        for (WebSocketSession connectedSession : roomSessions.get(roomId)) {
            connectedSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(newMessage)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        Long loginUserSeq = (Long) session.getAttributes().get("loginuser");
        String role = (String) session.getAttributes().get("role");

        // 퇴장 메시지 생성
        Message exitMessage = new Message(
                loginUserSeq.toString(),
                role.equals("SELLER") ? "판매자" : "구매자",
                role,
                loginUserSeq + " 님이 퇴장했습니다 (" + role + ")",
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