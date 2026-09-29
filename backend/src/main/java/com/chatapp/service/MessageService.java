package com.chatapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatapp.dto.MessageResponse;
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

        public List<MessageResponse> getMessages(
                        Long conversationId,
                        Long currentUserID) {

                // 1. Find a conversation
                Conversation conversation = conversationRepository
                                .findById(conversationId)
                                .orElseThrow(() -> new RuntimeException("Conversation not found"));

                // 2. Check if current user participat in that conversation
                boolean isParticipant = conversation.getUserOne().getId().equals(currentUserID)
                                || conversation.getUserTwo().getId().equals(currentUserID);
                if (!isParticipant) {
                        throw new RuntimeException("User is not part of that conversation");
                }

                // 3. get mesaages from conversation
                List<Message> messages = messageRepository.findByConversationIdOrderByCreateAtAsc(conversationId);

                // 4. converat Entity into DTO
                return messages.stream().map(MessageResponse::from).toList();
        }

}