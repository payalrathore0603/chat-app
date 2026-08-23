package com.chatapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Email is required") @Email(message = "Please enter valid email formate") String email,

        @NotBlank(message = "Password is required") String password) {

}
