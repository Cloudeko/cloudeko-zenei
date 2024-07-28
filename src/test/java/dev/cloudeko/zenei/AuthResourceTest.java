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
    @DisplayName("Create user via signup")
    void testCreateUser() {
        SignupRequest request = new SignupRequest("test-user", "test@test.com", "test-password");

        User user = given()
                .contentType("application/json")
                .body(request)
                .post("/signup")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(User.class);

        assertAll(
                () -> assertNotNull(user),
                () -> assertNotNull(user.getId()),
                () -> assertEquals(user.getUsername(), request.name()),
                () -> assertEquals(user.getEmail(), request.email())
        );
    }

    @Test
    @Order(2)
    @DisplayName("Retrieve a access token")
    void testGetAccessToken() {
        Token token = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Token.class);

        assertAll(
                () -> assertNotNull(token),
                () -> assertNotNull(token.getAccessToken()),
                () -> assertNotNull(token.getRefreshToken())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve a new access token using refresh token")
    void testGetAccessTokenUsingRefreshToken() {
        Token token = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Token.class);

        assertAll(
                () -> assertNotNull(token),
                () -> assertNotNull(token.getAccessToken()),
                () -> assertNotNull(token.getRefreshToken())
        );

        Token newToken = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", token.getRefreshToken())
                .post("/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Token.class);

        assertAll(
                () -> assertNotNull(newToken),
                () -> assertNotNull(newToken.getAccessToken()),
                () -> assertNotNull(newToken.getRefreshToken())
        );
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve a new access token using invalid refresh token")
    void testGetAccessTokenUsingInvalidRefreshToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", "invalid-refresh-token")
                .post("/token")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
