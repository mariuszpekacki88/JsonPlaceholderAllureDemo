package com.jsonplaceholder.user.put;

import com.jsonplaceholder.base.BaseTest;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class putTest extends BaseTest {

    private User user;
    private Address address;
    private Company company;
    private Geo geo;

    @BeforeEach
    public void beforeEach(){
        geo = new Geo();
        geo.setLat("-11.111");
        geo.setLng("99.999");

        address = new Address();
        address.setStreet("sezamkowa");
        address.setSuite("234234");
        address.setCity("new york");
        address.setZipcode("09-999111");
        address.setGeo(geo);

        company = new Company();
        company.setName("LuxMed1");
        company.setCatchPhrase("sasdasdasd1");
        company.setBs("asdasdsad1");

        user = new User();
        user.setName("Nazwa nowa Mariusz1");
        user.setUsername("mariuszpekacki1");
        user.setEmail("www@wp.pl1");
        user.setPhone("5110239901");
        user.setWebsite("www.wp@pl1");
        user.setCompany(company);
        user.setAddress(address);
    }

    @Test
    public void updateEmail()
    {
        Response response = given()
                .spec(reqSpec)
                .pathParam("id", 1)
                .body(user)
                .when()
                .put(BASE_URL + "/" + USERS + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("email")).isEqualTo(user.getEmail());
    }
}
