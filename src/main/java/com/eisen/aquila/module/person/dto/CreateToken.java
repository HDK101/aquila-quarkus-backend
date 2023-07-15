package com.eisen.aquila.module.person.dto;

import jakarta.validation.constraints.NotNull;

public class CreateToken {
    public enum Type {
        USER_CREDENTIALS,
        REFRESH_TOKEN
    }

    @NotNull
    public Type type;
    public String login;
    public String password;
    public String refreshToken;
}
