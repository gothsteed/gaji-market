package com.gaji.app.chatting.websockethandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.mongo.dto.MessageDTO;
import com.gaji.app.mongo.entity.Message;

@Component 
public class WebsocketEchoHandler extends TextWebSocketHandler {

	private List<WebSocketSession> connetedUsers = new ArrayList<>();
	
	@Autowired
	private ChattingMongoOperations chattingMongo;
	
	private MemberUserDetail userDetail;
	
	public void init() throws Exception {}
	
	// 클라이언트가 웹소켓서버에 연결했을 때의 작업 처리하기
	@Override
	public void afterConnectionEstablished(WebSocketSession wsession) throws Exception {
		
		connetedUsers.add(wsession);
		
		List<Message> list = chattingMongo.listChatting(); // 몽고DB에 저장되어진 채팅내용을 읽어온다.
		 
		SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREA);
		
		if(list != null && list.size() > 0) { // 이전에 나누었던 대화내용이 있다라면
			for(int i=0; i<list.size(); i++) {
				String str_created = sdfrmt.format(list.get(i).getCurrentTime()); // 대화내용을 나누었던 날짜를 읽어온다.
			
				boolean is_newDay = true; // 대화내용의 날짜가 같은 날짜인지 새로운 날짜인지 알기위한 용도
				
				if(i>0 && str_created.equals(sdfrmt.format(list.get(i-1).getCurrentTime()))) { // 다음 내용물에 있는 대화를 했던 날짜가 이전 내용물에 있는 대화를 했던 날짜와 같다라면
					is_newDay = false; // 이 대화내용은 새로운 날짜의 대화가 아님을 표시
				}
				
				if(is_newDay) {
					wsession.sendMessage(
							new TextMessage("<div style='text-align: center; background: #ccc;'>" + str_created + "</div>")
					);
				}
				
				Map<String, Object> map = wsession.getAttributes();
				
				if(userDetail.getUserId().equals(list.get(i).getSenderId())) {
					wsession.sendMessage(
							new TextMessage("<div style='background: #b287f7; color: #fff; display: inline-block; max-width: 60%; float: right; padding: 8px 10px; border-radius: 25px; word-break: break-all;'>" + list.get(i).getContent() + "</div><div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 8pt;'>" + list.get(i).getCurrentTime() + "</div><div style='clear: both;'>&nbsp;</div>")
					);
				}
				else { // 다른 사람이 작성한 채팅메시지일 경우
					wsession.sendMessage(
							new TextMessage("<div style='display: flex;'><span style='display: inline-block; margin-right: 2%;' class='profile_image'></span><div style='background: #eaebee; display: inline-block; max-width: 60%; padding: 8px 10px; border-radius: 25px; word-break: break-all;'>" + list.get(i).getContent() + "</div><div style='display: inline-block; padding: 20px 0 0 5px; font-size: 8pt;'>" + list.get(i).getCurrentTime() + "</div></div><div>&nbsp;</div>")
					);
				}
				
			} // end of for ----------
		}
	}
	
	// 클라이언트가 웹소켓 서버로 메시지를 보냈을 때의 Send 이벤트를 처리하기
	@Override
	public void handleTextMessage(WebSocketSession wsession, TextMessage textmessage) throws Exception { 
		
		MessageDTO message = MessageDTO.convertMessage(textmessage.getPayload());
		
		Date now = new Date(); 
		String currentTime = String.format("%tp %tl:%tM",now,now,now);
		
		
	}
	
	
}
