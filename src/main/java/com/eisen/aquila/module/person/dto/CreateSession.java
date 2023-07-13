package com.eisen.aquila.module.person.dto;

import jakarta.validation.constraints.NotNull;

public class CreateSession {
    @NotNull
    public String login;
    @NotNull
    public String password;
}
