package com.eisen.aquila.person;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Role;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ResponseBody;
import io.vertx.core.json.JsonObject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class RoleResourceTest {
    @Transactional
    static void createPerson() {
        Role role = Role.findRoleByNameIdOptional("client").orElse(new Role());
        Person person = Person.findByEmail("admin@admin.com").orElse(new Person());

        role = new Role();
        role.nameId = "client";
        role.nameDisplay = "Cliente";
        role.persist();

        person = new Person();
        person
                .name("Admin")
                .login("admin@admin.com")
                .email("admin@admin.com")
                .password("admin")
                .birth(LocalDate.now())
                .phone("18999999999")
                .roles(new HashSet<Role>(List.of(role)));
        person.persist();
    }

    @Transactional
    static void clearDatabase() {

    }

    @BeforeAll
    @Transactional
    static void beforeEach() {
        createPerson();
    }

    @Test
    @Transactional
    void test_list_roles() {
        JsonObject jsonObject = new JsonObject().put("login", "admin@admin.com").put("password", "admin");
        System.out.println(jsonObject.toString());
        var extractableResponse = given()
        .header("Content-Type", "application/json")
        .body(jsonObject.toString()).when().post("/persons/sessions").then().extract();

        System.out.println(extractableResponse.asPrettyString());

        //given().when().get("/api/roles").then().statusCode(200);
    }
}
