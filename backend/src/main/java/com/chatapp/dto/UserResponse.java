package com.chatapp.dto;

import java.time.LocalDateTime;

import com.chatapp.entity.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createAt) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreateAt());
    }

}
