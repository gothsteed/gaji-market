package com.gaji.app.mongo.handshaker;

import com.gaji.app.auth.dto.MemberUserDetail;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.net.URI;
import java.util.Map;

@Component
public class ChatroomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // 인증된 사용자 정보 저장
            if (principal instanceof MemberUserDetail userDetails) {
                attributes.put("loginuser", userDetails.getMemberSeq());
                attributes.put("role", userDetails.getRole());  // 역할 정보 추가
            } else {
                attributes.put("loginuser", principal.toString());
            }
        }

        // 쿼리 파라미터 처리
        URI uri = request.getURI();
        String query = uri.getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                if (data.length == 2) {
                    attributes.put(data[0], data[1]);
                }
            }
        }

        // roomId가 잘 저장되었는지 확인하는 로깅
        String roomId = (String) attributes.get("roomId");
        if (roomId == null) {
            System.out.println("roomId is missing from WebSocket request");
        }


        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}