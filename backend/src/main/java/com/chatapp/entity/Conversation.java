package com.chatapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_one_id", nullable = false)
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user_two_id", nullable = false)
    private User userTwo;

    @Column(nullable = false)
    private LocalDateTime createAt;

    public Conversation() {
        this.createAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUserOne() {
        return userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    public LocalDateTime getcrDateTime() {
        return createAt;
    }
}

// conversations
// -----------------------------------------
// id | user_one_id | user_two_id | created_at
// 1 | 1 | 2 | ...

// userOne aur userTwo actual User objects hain, lekin database mein unke IDs
// store honge.

// @ManyToOne kyun?

// Ek user bahut saari conversations ka part ho sakta hai:

// Payal
// ├── Payal ↔ Rahul
// ├── Payal ↔ Amit
// └── Payal ↔ Neha

// So:

// Many conversations → One User

// @JoinColumn
// @JoinColumn(name = "user_one_id")

// ka matlab:

// conversations table mein user_one_id naam ka foreign-key column banao jo
// users ko refer kare.
