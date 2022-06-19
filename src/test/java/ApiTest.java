import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class ApiTest {
    private static Connection connection;
    private static int testCountryID;


    @BeforeAll
    public static void testsPreparations() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost/app-db",
                "app-db-admin",
                "P@ssw0rd"
        );
        Statement alterTable = connection.createStatement();
        alterTable.execute("alter table country " +
                "ALTER COLUMN id " +
                "ADD generated always as identity (START WITH 11)");

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("admin");
        RestAssured.authentication = authScheme;

    }

    @BeforeAll
    public static void errorLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void createTestsData() throws SQLException {
        PreparedStatement sql = connection.prepareStatement(
                "INSERT INTO country(country_name) VALUES(?)",
                Statement.RETURN_GENERATED_KEYS);
        sql.setString(1, "TO");
        sql.executeUpdate();
        ResultSet result = sql.getGeneratedKeys();
        result.next();
        testCountryID = result.getInt(1);
    }

    @AfterEach
    public void clearTestsData() throws SQLException {
        Statement check = connection.createStatement();
        ResultSet result = check.executeQuery("SELECT * FROM country where id > 10");
        if (result.next()) {
            PreparedStatement delete = connection.prepareStatement("DELETE FROM country WHERE ID >10");
            delete.executeUpdate();
        }
        ;
    }

    @AfterAll
    public static void disconnect() throws SQLException {
        Statement alterTable = connection.createStatement();
        alterTable.execute("alter table country ALTER COLUMN id DROP IDENTITY");
        connection.close();
    }

    @Test
    public void findCountry() {
        when()
                .get("api/countries?countryName.contains=TO").
                then()
                .statusCode(200).body("[0].id", is(testCountryID));
    }

    @Test
    public void setCountry() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"id\": " + testCountryID + ",\n" +
                        "  \"countryName\": \"TT\"\n" +
                        "}")
                .when()
                .patch("api/countries/" + testCountryID)
                .then()
                .statusCode(200)
                .body("id", is(testCountryID),
                        "countryName", is("TT"));
    }

    @Test
    public void deleteCountry() {
        when()
                .delete("api/countries/" + testCountryID).then().statusCode(204);

        when()
                .get("api/countries?id.equals=" + testCountryID).
                then()
                .statusCode(200).body("[0]", isEmptyOrNullString());
    }

    @Nested
    class nonAlteredTable {
        @BeforeEach
        public void clearIdentity() throws SQLException {
            Statement alterTable = connection.createStatement();
            alterTable.execute("alter table country ALTER COLUMN id DROP IDENTITY");
        }

        @AfterEach
        public void backingIdentity() throws SQLException {
            Statement maxID = connection.createStatement();
            ResultSet maxIDResult = maxID.executeQuery("select max(ID) from country");
            maxIDResult.next();
            String sql = "alter table country " +
                    "ALTER COLUMN id " +
                    "ADD generated always as identity (START WITH "+maxIDResult.getLong(1)+")";
            Statement alterTable = connection.createStatement();
            alterTable.execute(sql);
        }

        @Test
        public void createCountry() {
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"II\"\n" +
                            "}")
                    .when()
                    .post("api/countries")
                    .then()
                    .statusCode(201)
                    .body("id", not(empty()));


        }
    }
}
