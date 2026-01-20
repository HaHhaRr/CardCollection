package ru.hahharr.cardcollection.models.dto.response;

import lombok.Getter;

@Getter
public class TokenResponseDto {

    private final String accessToken;

    private final String refreshToken;

    public TokenResponseDto(String token, String refreshToken) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }
}
