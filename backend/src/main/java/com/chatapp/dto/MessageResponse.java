package com.chatapp.dto;

import java.time.LocalDateTime;

import com.chatapp.entity.Message;

public record MessageResponse(
        Long id,
        Long conversationId,
        Long senderId,
        String content,
        LocalDateTime createdAt) {
    public static MessageResponse from(Message message) {
        return new MessageResponse(message.getId(), message.getConversation().getId(), message.getSender().getId(),
                message.getContent(), message.getCreateAt());
    }
}
