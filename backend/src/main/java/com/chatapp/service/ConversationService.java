package com.chatapp.service;

import org.springframework.stereotype.Service;

import com.chatapp.dto.CreateConversationRequest;
import com.chatapp.entity.Conversation;
import com.chatapp.entity.User;
import com.chatapp.repository.ConversationRepository;
import com.chatapp.repository.UserRepository;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Conversation createConversation(
            CreateConversationRequest request,
            Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        User othUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUserId.equals(othUser.getId())) {
            throw new RuntimeException(
                    "Cannot create conversation with yourself");
        }

        Conversation conversation = new Conversation();

        conversation.setUserOne(currentUser);
        conversation.setUserTwo(othUser);

        return conversationRepository.save(conversation);
    }
}
