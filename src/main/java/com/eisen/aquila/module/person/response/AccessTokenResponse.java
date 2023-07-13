package com.eisen.aquila.module.person.response;

public class AccessTokenResponse {
    public String refreshToken;

    public AccessTokenResponse(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
