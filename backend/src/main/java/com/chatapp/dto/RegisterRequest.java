package com.chatapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank(message = "Name is required") @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters") String name,

        @NotBlank(message = "Email is required") @Email(message = "Invalid Email format") String email,

        @NotBlank(message = "Password is required") @Size(min = 8, max = 15, message = "Password must be between 8 to 15") String password) {

}
