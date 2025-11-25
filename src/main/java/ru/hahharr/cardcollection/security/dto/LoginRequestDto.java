package ru.hahharr.cardcollection.security.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;

    private String password;
}