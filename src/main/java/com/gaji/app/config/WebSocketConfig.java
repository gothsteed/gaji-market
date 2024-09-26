package com.gaji.app.config;

import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.mongo.controller.ChatWebSocketHandler;
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
    private final MemberRepository memberRepository;

    public WebSocketConfig(MessageRepository messageRepository, MemberRepository memberRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;

    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler(), "/multichatstart")
                .addInterceptors(new ChatroomHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler(messageRepository, memberRepository);
    }

}
