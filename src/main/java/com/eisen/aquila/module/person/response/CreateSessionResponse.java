package com.eisen.aquila.module.person.response;

public class CreateSessionResponse {
    public String refreshToken;
    public String accessToken;

    public CreateSessionResponse refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
    public CreateSessionResponse accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
