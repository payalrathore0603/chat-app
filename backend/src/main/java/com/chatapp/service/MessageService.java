package com.chatapp.service;

import org.springframework.stereotype.Service;

import com.chatapp.dto.SendMessageRequest;
import com.chatapp.entity.Conversation;
import com.chatapp.entity.Message;
import com.chatapp.entity.User;
import com.chatapp.repository.ConversationRepository;
import com.chatapp.repository.MessageRepository;
import com.chatapp.repository.UserRepository;

@Service
public class MessageService {

        private final MessageRepository messageRepository;
        private final ConversationRepository conversationRepository;
        private final UserRepository userRepository;

        public MessageService(
                        MessageRepository messageRepository,
                        ConversationRepository conversationRepository,
                        UserRepository userRepository) {

                this.messageRepository = messageRepository;
                this.conversationRepository = conversationRepository;
                this.userRepository = userRepository;
        }

        public Message sendMessage(
                        SendMessageRequest request,
                        Long senderId) {

                Conversation conversation = conversationRepository
                                .findById(request.conversationId())
                                .orElseThrow(() -> new RuntimeException("Conversation not found"));

                User sender = userRepository
                                .findById(senderId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                boolean isParticipant = conversation.getUserOne().getId().equals(senderId)
                                || conversation.getUserTwo().getId().equals(senderId);

                if (!isParticipant) {
                        throw new RuntimeException(
                                        "User is not part of this conversation");
                }

                Message message = new Message();

                message.setConversation(conversation);
                message.setSender(sender);
                message.setContent(request.content());

                return messageRepository.save(message);
        }
}