package ru.hahharr.cardcollection.models.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;

    private String password;
}
