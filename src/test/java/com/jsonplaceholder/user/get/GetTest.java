package com.jsonplaceholder.user.get;

import com.jsonplaceholder.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class GetTest extends BaseTest {

    @Test
    public void readAllUsers ()
    {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertThat(10).isEqualTo(names.size());
    }

    @Test
    public void readOneUser(){
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat("Leanne Graham").isEqualTo(json.get("name"));
        assertThat("Kulas Light").isEqualTo(json.get("address.street"));
    }

    @Test
    public void readOneUserWithPathVariables() {

        Response response = given()
                .spec(reqSpec)
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat("Leanne Graham").isEqualTo(json.get("name"));
        assertThat("Kulas Light").isEqualTo(json.get("address.street"));
    }

    @Test
    public void readUserWithQueryParams()
    {
        Response response = given()
                .spec(reqSpec)
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat("Leanne Graham").isEqualTo(json.getList("name").get(0));
        assertThat("Bret").isEqualTo(json.getList("username").get(0));
    }
}
