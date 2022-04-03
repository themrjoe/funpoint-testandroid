package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String username;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
}
