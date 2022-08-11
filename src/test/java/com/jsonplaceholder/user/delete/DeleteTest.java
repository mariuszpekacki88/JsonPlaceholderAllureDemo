package com.jsonplaceholder.user.delete;

import com.jsonplaceholder.base.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class DeleteTest extends BaseTest {

    @Test
    public void deleteUser(){

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteAllUser(){

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + USERS)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
