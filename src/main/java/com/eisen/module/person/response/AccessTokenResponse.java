package com.eisen.module.person.response;

public class AccessTokenResponse {
    public String refreshToken;

    public AccessTokenResponse(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
