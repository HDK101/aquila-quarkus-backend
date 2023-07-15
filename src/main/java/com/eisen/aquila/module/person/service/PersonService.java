package com.eisen.aquila.module.person.service;

import com.eisen.aquila.module.person.dto.CreateToken;
import com.eisen.aquila.module.person.response.TokenResponse;

public interface PersonService {
    TokenResponse createToken(CreateToken createToken);
}
