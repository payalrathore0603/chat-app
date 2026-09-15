package com.chatapp.dto;

import jakarta.validation.constraints.NotNull;

public record CreateConversationRequest(@NotNull(message = "User ID is required") Long userId) {

}
