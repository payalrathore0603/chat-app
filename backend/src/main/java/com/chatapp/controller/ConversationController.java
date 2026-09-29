package com.chatapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.chatapp.dto.MessageResponse;

import com.chatapp.dto.ConversationResponse;
import com.chatapp.dto.CreateConversationRequest;
import com.chatapp.entity.Conversation;
import com.chatapp.service.ConversationService;
import com.chatapp.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;
    private final MessageService messageService;

    public ConversationController(ConversationService conversationService, MessageService messageService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conversation createConversation(
            @Valid @RequestBody CreateConversationRequest request,
            Authentication authentication) {
        Long currentUserId = (Long) authentication.getPrincipal();
        return conversationService.createConversation(request, currentUserId);
    }

    @GetMapping
    public List<ConversationResponse> getMyConversations(
            Authentication authentication) {
        Long currentUserId = (Long) authentication.getPrincipal();

        return conversationService.getMyConversation(currentUserId);
    }

    @GetMapping("/{conversationId}/messages")
    public List<MessageResponse> getMessages(
            @PathVariable Long conversationId,
            Authentication authentication) {
        Long currentUserId = (Long) authentication.getPrincipal();

        return messageService.getMessages(conversationId, currentUserId);
    }

}
