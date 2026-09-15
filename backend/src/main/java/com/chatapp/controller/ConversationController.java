package com.chatapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.dto.CreateConversationRequest;
import com.chatapp.entity.Conversation;
import com.chatapp.service.ConversationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conversation createConversation(
            @Valid @RequestBody CreateConversationRequest request,
            Authentication authentication) {
        Long currentUserId = (Long) authentication.getPrincipal();
        return conversationService.createConversation(request, currentUserId);
    }

}
