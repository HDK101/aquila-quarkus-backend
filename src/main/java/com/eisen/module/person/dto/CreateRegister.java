package com.eisen.module.person.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateRegister {
    @NotNull
    public String name;
    @Email
    public String email;
    @NotNull
    @Length(min = 6, max = 32)
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W_]).+", message = "Password must contain a uppercase letter, a special character and a number")
    public String password;
    @NotNull
    public String phone;
    @NotNull
    public LocalDate birth;
}
