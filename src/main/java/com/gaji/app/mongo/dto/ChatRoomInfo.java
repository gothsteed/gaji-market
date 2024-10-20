package com.gaji.app.mongo.dto;

import com.gaji.app.mongo.entity.ChatRoom;
import com.gaji.app.mongo.entity.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomInfo {
    private Long sellerId;
    private Long buyerId;

    public ChatRoomInfo(Long sellerId, Long buyerId) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
    }


}
