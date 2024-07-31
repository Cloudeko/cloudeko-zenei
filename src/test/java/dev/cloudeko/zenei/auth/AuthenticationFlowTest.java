package dev.cloudeko.zenei.auth;

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
    @DisplayName("Create user via registerAttempt (POST /registerAttempt) should return (200 OK)")
    void testCreateUser() {
        given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formParam("username", "test-user")
                .formParam("email", "test@test.com")
                .formParam("password", "test-password")
                .formParam("strategy", "PASSWORD")
                .post("/user")
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
    @DisplayName("Validate user email (POST /verify-email) should return redirect (303 SEE_OTHER)")
    void testVerifyEmail() {
        assertEquals(1, mailbox.getTotalMessagesSent());

        final var sentMails = mailbox.getMailMessagesSentTo("test@test.com");
        assertEquals(1, sentMails.size());

        final var mail = sentMails.getFirst();

        assertNotNull(mail);
        assertEquals("Welcome to Zenei", mail.getSubject());

        final var body = mail.getHtml();
        assertNotNull(body);

        var verificationLink = body.substring(body.indexOf("href=\"") + 6, body.indexOf("\">"));
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
    @DisplayName("Retrieve a access token using username and password (POST /token) should return (200 OK)")
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
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                );
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve a new access token using refresh token (POST /token) should return (200 OK)")
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
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                )
                .extract().as(Token.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", token.getRefreshToken())
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "accessToken", notNullValue(),
                        "refreshToken", notNullValue()
                );
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve a new access token using invalid refresh token (POST /token) should return (401 UNAUTHORIZED)")
    void testGetAccessTokenUsingInvalidRefreshToken() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", "invalid-refresh-token")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
