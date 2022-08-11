package com.jsonplaceholder.user.post;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class PostTest extends BaseTest {

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
        address.setStreet("Celulozy");
        address.setSuite("234234");
        address.setCity("Warsaw");
        address.setZipcode("09-999");
        address.setGeo(geo);

        company = new Company();
        company.setName("LuxMed");
        company.setCatchPhrase("sasdasdasd");
        company.setBs("asdasdsad");

        user = new User();
        user.setName("Nazwa nowa Mariusz");
        user.setUsername("mariuszpekacki");
        user.setEmail("www@wp.pl");
        user.setPhone("511023990");
        user.setWebsite("www.wp@pl");
        user.setCompany(company);
        user.setAddress(address);
    }

    @Test()
    public void createNewUser(){

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
    }

    @DisplayName("Create user with specific email")
    @ParameterizedTest(name = "Create user with specific email:{0}")
    @MethodSource("createUserEmailData")
    public void createOrganization(String email){

        user.setEmail(email);        //napisanie pola co jest wczesniej na górze

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
        Assertions.assertThat(json.getString("email")).isEqualTo(user.getEmail());
    }

    private static Stream<Arguments> createUserEmailData(){
        return Stream.of(
                Arguments.of("test@wp.pl"),
                Arguments.of("test@aap.com"),
                Arguments.of("test@aa.eu"));
    }
}
