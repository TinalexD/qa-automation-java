import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class ApiTest {

    @BeforeAll
    public static void getAuth() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("admin");
        RestAssured.authentication = authScheme;
    }

    @BeforeAll
    public static void errorLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void createCountry() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"countryName\": \"RU\"\n" +
                        "}")
        .when()
                .post("api/countries")
        .then()
                .statusCode(201)
                .body("id", not(empty()));

    }

    @Test
    public void findCountry() {
        when()
                .get("api/countries?countryName.contains=st").
        then()
                .statusCode(200).body("[0].id", is(3));
    }

    @Test
    public void setCountry() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"countryName\": \"nl\"\n" +
                        "}")
        .when()
                .patch("api/countries/1")
        .then()
                .statusCode(200)
                .body("id", is(1),
                        "countryName", is("nl"));
    }

    @Test
    public void deleteCountry() {
        when()
                .delete("api/countries/2").then().statusCode(204);

        when()
                .get("api/countries?id.equals=2").
        then()
                .statusCode(200).body("[0]", isEmptyOrNullString());
    }
}
