package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.application.web.model.response.SessionTokenResponse;
import dev.cloudeko.zenei.profile.MailingEnabledProfile;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestProfile(MailingEnabledProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationFlowWithEnabledMailingTest {

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
                .formParam("username", "test-user2")
                .formParam("email", "test@test.com")
                .formParam("password", "test-password")
                .formParam("strategy", "PASSWORD")
                .post("/frontend/register")
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
                .post("/frontend/register")
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
                .formParam("identifier", "test@test.com")
                .formParam("password", "test-password")
                .post("/frontend/login")
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
                .formParam("identifier", "test@test.com")
                .formParam("password", "invalid-password")
                .post("/frontend/login")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve a new access token using refresh token (POST /user/token) should return (200 OK)")
    void testGetAccessTokenUsingRefreshToken() {
        final var token = given()
                .formParam("identifier", "test@test.com")
                .formParam("password", "test-password")
                .post("/frontend/login")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                )
                .extract().as(SessionTokenResponse.class);

        given()
                .queryParam("refresh_token", token.getRefreshToken())
                .post("/frontend/session/refresh")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                );
    }

    @Test
    @Order(5)
    @DisplayName("Try to retrieve a new access token using invalid refresh token (POST /user/token) should return (401 UNAUTHORIZED)")
    void testGetAccessTokenUsingInvalidRefreshToken() {
        given()
                .queryParam("refresh_token", "invalid-refresh-token")
                .post("/frontend/session/refresh")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve user information (GET /user) should return (200 OK)")
    void testGetUserInfo() {
        final var token = given()
                .formParam("identifier", "test@test.com")
                .formParam("password", "test-password")
                .post("/frontend/login")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(SessionTokenResponse.class);

        given()
                .header("Authorization", "Bearer " + token.getAccessToken())
                .get("/frontend/user")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "id", notNullValue(),
                        "username", notNullValue(),
                        "primary_email_address", notNullValue(),
                        "email_addresses", notNullValue(),
                        "email_addresses.size()", greaterThanOrEqualTo(1),
                        "email_addresses.getFirst().email_verified", equalTo(true)
                );
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve user information without token (GET /user) should return (401 UNAUTHORIZED)")
    void testGetUserInfoWithoutToken() {
        given()
                .get("/frontend/user")
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
