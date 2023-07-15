package com.eisen.aquila.person;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Role;
import com.eisen.aquila.person.admin.CreateDefault;
import com.eisen.aquila.person.admin.CreateTokenByCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.quarkus.builder.Json;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class PersonSessionResourceTest {
    @Inject
    ObjectMapper objectMapper;

    private final String WRONG_ADMIN_LOGIN = "adminn@admin.com";
    private final String WRONG_ADMIN_PASSWORD = "adminn";

    @BeforeAll
    @Transactional
    static void beforeEach() {
        CreateDefault.create();
    }

    @Test
    @Transactional
    void test_create_token_by_credentials() {
        JsonObject bodyObject = CreateTokenByCredentials.create(CreateDefault.ADMIN_EMAIL, CreateDefault.ADMIN_PASSWORD);

        assertNotNull(bodyObject.getString("accessToken"));
        assertNotNull(bodyObject.getString("refreshToken"));
    }

    @Test
    @Transactional
    void test_try_to_create_token_with_invalid_login() {
        CreateTokenByCredentials.givenCredentialsWhenCreateToken(WRONG_ADMIN_LOGIN, CreateDefault.ADMIN_PASSWORD)
            .then()
            .statusCode(400);
    }

    @Test
    @Transactional
    void test_try_to_create_token_with_invalid_password() {
        CreateTokenByCredentials.givenCredentialsWhenCreateToken(CreateDefault.ADMIN_EMAIL, WRONG_ADMIN_PASSWORD)
            .then()
            .statusCode(400);
    }

    @Test
    @Transactional
    void test_try_to_create_token_with_invalid_credentials() {
        CreateTokenByCredentials.givenCredentialsWhenCreateToken(WRONG_ADMIN_LOGIN, WRONG_ADMIN_PASSWORD)
            .then()
            .statusCode(400);
    }
}
