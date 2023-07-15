package com.eisen.aquila.person;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Role;
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
import io.restassured.response.ResponseBody;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class PersonSessionResourceTest {
    @Inject
    ObjectMapper objectMapper;

    @BeforeAll
    @Transactional
    static void beforeEach() {
        CreateDefaultAdmin.create();
    }

    @Test
    @Transactional
    void test_create_token_by_credentials() {
        JsonObject jsonObject = new JsonObject()
            .put("login", "admin@admin.com")
            .put("password", "admin")
            .put("type", "USER_CREDENTIALS");
        var extractableResponse = given()
        .header("Content-Type", "application/json")
        .body(jsonObject.toString()).when().post("/persons/token").then().statusCode(201).extract();

        String body =  extractableResponse.body().asString();

        JsonObject bodyObject = new JsonObject(body);
    
        assertNotNull(bodyObject.getString("accessToken"));
        assertNotNull(bodyObject.getString("refreshToken"));
    }
}
