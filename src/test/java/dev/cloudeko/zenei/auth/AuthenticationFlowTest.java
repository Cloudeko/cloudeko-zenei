package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.application.web.model.response.TokenResponse;
import dev.cloudeko.zenei.domain.model.Token;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationFlowTest {

    @ConfigProperty(name = "quarkus.http.test-port")
    int quarkusPort;

    @Inject
    MockMailbox mailbox;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(1)
    @DisplayName("Create user via email and password (POST /user) should return (200 OK)")
    void testCreateUser() {
        given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formParam("username", "test-user2")
                .formParam("email", "test@test.com")
                .formParam("password", "test-password")
                .formParam("strategy", "PASSWORD")
                .post("/user")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "id", notNullValue(),
                        "username", notNullValue(),
                        "primary_email_address", notNullValue()
                );
    }

    @Test
    @Order(1)
    @DisplayName("Create user via email and password and redirect (POST /user) should return redirect (303 SEE_OTHER)")
    void testCreateUserWithRedirect() {
        given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formParam("username", "test-user")
                .formParam("email", "test2@test.com")
                .formParam("password", "test-password")
                .formParam("strategy", "PASSWORD")
                .formParam("redirect_to", "https://google.com")
                .post("/user")
                .then()
                .statusCode(Response.Status.TEMPORARY_REDIRECT.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Validate user email (POST /user/verify-email) should return (204 NO_CONTENT)")
    void testVerifyEmailWithoutRedirect() {
        var verificationLink = getVerificationLink("test@test.com");
        assertNotNull(verificationLink);

        verificationLink = verificationLink.replace("http://localhost:8080", "http://localhost:" + quarkusPort);

        given()
                .post(verificationLink)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Validate user email with redirect (POST /user/verify-email) should return redirect (303 SEE_OTHER)")
    void testVerifyEmail() {
        var verificationLink = getVerificationLink("test2@test.com");
        assertNotNull(verificationLink);

        verificationLink = verificationLink.replace("http://localhost:8080", "http://localhost:" + quarkusPort);

        given()
                .queryParam("redirect_to", "https://google.com")
                .post(verificationLink)
                .then()
                .statusCode(Response.Status.TEMPORARY_REDIRECT.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve a access token using username and password (POST /user/token) should return (200 OK)")
    void testGetAccessToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                );
    }

    @Test
    @Order(3)
    @DisplayName("Try to retrieve a access token using invalid username and password (POST /user/token) should return (401 UNAUTHORIZED)")
    void testGetAccessTokenInvalid() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "invalid-password")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve a new access token using refresh token (POST /user/token) should return (200 OK)")
    void testGetAccessTokenUsingRefreshToken() {
        final var token = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                )
                .extract().as(TokenResponse.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", token.getRefreshToken())
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                );
    }

    @Test
    @Order(5)
    @DisplayName("Try to retrieve a new access token using invalid grant type (POST /user/token) should return (400 BAD_REQUEST)")
    void testGetAccessTokenUsingInvalidGrantType() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "invalid-grant-type")
                .queryParam("username", "invalid-username")
                .queryParam("password", "invalid-password")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(5)
    @DisplayName("Try to retrieve a new access token using invalid refresh token (POST /user/token) should return (401 UNAUTHORIZED)")
    void testGetAccessTokenUsingInvalidRefreshToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", "invalid-refresh-token")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve user information (GET /user) should return (200 OK)")
    void testGetUserInfo() {
        final var token = given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "password")
                .queryParam("username", "test@test.com")
                .queryParam("password", "test-password")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(TokenResponse.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .get("/user")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "id", notNullValue(),
                        "username", notNullValue(),
                        "primary_email_address", notNullValue()
                );
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve user information without token (GET /user) should return (401 UNAUTHORIZED)")
    void testGetUserInfoWithoutToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/user")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    private String getVerificationLink(String email) {
        final var sentMails = mailbox.getMailMessagesSentTo(email);
        assertEquals(1, sentMails.size());

        final var mail = sentMails.getFirst();

        assertNotNull(mail);
        assertEquals("Welcome to Zenei", mail.getSubject());

        final var body = mail.getHtml();
        assertNotNull(body);

        return body.substring(body.indexOf("href=\"") + 6, body.indexOf("\">"));
    }
}
