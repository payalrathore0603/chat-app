package com.chatapp.repository;

import com.chatapp.entity.Conversation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository
                extends JpaRepository<Conversation, Long> {

        List<Conversation> findByUserOneIdOrUserTwoId( // Ye existing conversations ki list nikalne ke liye hai.
                        Long userOneId,
                        Long userTwoId);

        @Query("""
                        SELECT c FROM Conversation c
                              WHERE (c.userOne.id = :userOneId AND c.userTwo.id = :userTwoId)
                                 OR (c.userOne.id = :userTwoId AND c.userTwo.id = :userOneId)  """)

        Optional<Conversation> findExistingConversation(
                        @Param("userOneId") Long userOneId,
                        @Param("userTwoId") Long userTwoId);
}

// findByUserOneIdAndUserTwoId Exact pair + exact order
// findByUserOneIdOrUserTwoId Current user's all conversations
// findExistingConversation Pooja + Rahul ki existing conversation check, order
// doesn't matter
// automatically convert into this
// SELECT *
// FROM conversations
// WHERE user_one_id = 3
// OR user_two_id = 3;

// conversation_id | user_one | user_two
// --------------------------------------
// 1 | 3 | 2
// 2 | 3 | 4
// 3 | 5 | 3
// 4 | 2 | 4

// Pooja ko milega:

// 1 → Pooja ↔ Rahul
// 2 → Pooja ↔ Amit
// 3 → Neha ↔ Pooja

// Conversation 4 nahi milegi.
