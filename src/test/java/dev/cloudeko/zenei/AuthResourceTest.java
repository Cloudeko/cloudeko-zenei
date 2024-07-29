package dev.cloudeko.zenei;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthResourceTest {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(1)
    @DisplayName("Create user via signup (POST /signup) should return (200 OK)")
    void testCreateUser() {
        SignupRequest request = new SignupRequest("test-user", "test@test.com", "test-password");

        given()
                .contentType("application/json")
                .body(request)
                .post("/signup")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "id", notNullValue(),
                        "username", notNullValue(),
                        "email", notNullValue()
                );
    }

    @Test
    @Order(2)
    @DisplayName("Retrieve a access token using username and password (POST /token) should return (200 OK)")
    void testGetAccessToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                );
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve a new access token using refresh token (POST /token) should return (200 OK)")
    void testGetAccessTokenUsingRefreshToken() {
        Token token = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                )
                .extract().as(Token.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", token.getRefreshToken())
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                );
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve a new access token using invalid refresh token (POST /token) should return (401 UNAUTHORIZED)")
    void testGetAccessTokenUsingInvalidRefreshToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", "invalid-refresh-token")
                .post("/token")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
