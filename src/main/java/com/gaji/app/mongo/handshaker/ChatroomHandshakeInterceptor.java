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

        // Get the authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Store authenticated user information in WebSocket attributes
            if (principal instanceof MemberUserDetail userDetails) {
                attributes.put("loginuser", userDetails.getMemberSeq());
            } else {
                attributes.put("loginuser", principal.toString());
            }
        }

        // Get any query parameters and store them in attributes
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

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}