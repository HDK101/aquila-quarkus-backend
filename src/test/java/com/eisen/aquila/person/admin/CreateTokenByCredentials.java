package com.eisen.aquila.person.admin;

import io.vertx.core.json.JsonObject;
import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CreateTokenByCredentials {
    public static Response givenCredentialsWhenCreateToken(String login, String password) {
        JsonObject jsonObject = new JsonObject()
                .put("login", login)
                .put("password", password)
                .put("type", "USER_CREDENTIALS");

        return given()
            .header("Content-Type", "application/json")
            .body(jsonObject.toString()).when().post("/persons/token");
    }

    public static JsonObject create(String login, String password) {
        ExtractableResponse<Response> extractableResponse = givenCredentialsWhenCreateToken(login, password)
            .then().statusCode(201).extract();

        String bodyRaw = extractableResponse.body().asString();

        return new JsonObject(bodyRaw);
    }
}
