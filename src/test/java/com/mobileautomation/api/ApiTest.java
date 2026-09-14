package com.mobileautomation.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @Test
    @Tag("api")
    void getPostById_shouldReturnStatus200AndId1() {
        given()
                .when()
                .get("http://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }
}

