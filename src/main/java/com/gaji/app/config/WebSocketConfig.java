package com.gaji.app.config;

import com.gaji.app.chatting.websockethandler.WebsocketEchoHandler;

import com.gaji.app.mongo.handshaker.ChatroomHandshakeInterceptor;
import com.gaji.app.mongo.repository.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageRepository messageRepository;

    public WebSocketConfig(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebsocketEchoHandler(), "/gaji/multichatstart")
                .addInterceptors(new ChatroomHandshakeInterceptor());
    }




}
