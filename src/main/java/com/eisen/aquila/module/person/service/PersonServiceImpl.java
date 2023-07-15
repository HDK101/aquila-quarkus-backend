package com.eisen.aquila.module.person.service;

import java.time.Duration;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import com.eisen.aquila.common.exception.DomainException;
import com.eisen.aquila.module.person.dto.CreateToken;
import com.eisen.aquila.module.person.exception.PersonTokenParseException;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Session;
import com.eisen.aquila.module.person.response.TokenResponse;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {
    private final Duration refreshMaxDuration = Duration.ofHours(1);
    private final Duration accessMaxDuration = Duration.ofSeconds(30);

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    @Inject
    JWTParser jwtParser;

    private JsonWebToken parseToken(String token) {
        try {
            return jwtParser.parse(token);
        } catch (ParseException e) {
            throw new PersonTokenParseException("Could not parse JWT Token.", e);
        }
    }

    private Person retrievePerson(CreateToken createToken) {
        if (createToken.type.equals(CreateToken.Type.USER_CREDENTIALS)) {
            return Person.findAndcheckCredentials(createToken.login, createToken.password);
        }

        JsonWebToken token = parseToken(createToken.refreshToken);
        Long personId = Long.valueOf(token.getClaim("personId"));

        return Person.findById(personId);
    }

    private Session createOrRetrieveSession(CreateToken createToken, Person person) {
        if (createToken.type.equals(CreateToken.Type.USER_CREDENTIALS)) {
            return Session.createFor(person);
        }

        JsonWebToken token = parseToken(createToken.refreshToken);
        Long sessionId = Long.valueOf(token.getClaim("sessionId"));
        return Session.findById(sessionId);
    }

    private String createAccessToken(Person person, Session session) {
        return Jwt.issuer(issuer)
        .expiresIn(accessMaxDuration)
        .upn(person.email)
        .groups(person.getSetOfRolesNameId())
        .claim("sessionId", session.id)
        .claim("personId", person.id)
        .sign();
    }

    private String createRefreshToken(Person person, Session session) {
        return Jwt.issuer(issuer)
        .expiresIn(refreshMaxDuration)
        .upn(person.email)
        .groups(person.getSetOfRolesNameId())
        .claim("sessionId", session.id)
        .claim("personId", person.id)
        .sign();
    }

    @Transactional
    @Override
    public TokenResponse createToken(CreateToken createToken) {
        Person person = retrievePerson(createToken);
        Session session = createOrRetrieveSession(createToken, person);

        String accessToken = createAccessToken(person, session);
        String refreshToken = createRefreshToken(person, session);

        TokenResponse tokenResponse = new TokenResponse()
            .refreshToken(refreshToken)
            .refreshExpiresIn(String.valueOf(refreshMaxDuration.toSeconds()))
            .accessToken(accessToken)
            .accessExpiresIn(String.valueOf(accessMaxDuration.toSeconds()));

        return tokenResponse;
    }
}
