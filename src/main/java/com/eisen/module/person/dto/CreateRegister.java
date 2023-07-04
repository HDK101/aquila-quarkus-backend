package com.eisen.module.person.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;

public class CreateRegister {
    public String name;
    @Email
    public String email;
    public String password;
    public String phone;
    public LocalDate birth;
}
