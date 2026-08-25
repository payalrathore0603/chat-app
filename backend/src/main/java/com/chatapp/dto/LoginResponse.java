package com.chatapp.dto;

public record LoginResponse(
        String accessToken,
        UserResponse user) {

}
