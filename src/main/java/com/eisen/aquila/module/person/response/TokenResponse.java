package com.eisen.aquila.module.person.response;

public class TokenResponse {
    public String refreshToken;
    public String refreshExpiresIn;
    public String accessToken;
    public String accessExpiresIn;

    public TokenResponse refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public TokenResponse refreshExpiresIn(String refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
        return this;
    }

    public TokenResponse accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public TokenResponse accessExpiresIn(String accessExpiresIn) {
        this.accessExpiresIn = accessExpiresIn;
        return this;
    }
}
