package com.eisen.module.person.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateSession {
    @NotNull
    public String login;
    @NotNull
    public String password;
}
