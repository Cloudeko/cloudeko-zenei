package dev.cloudeko.zenei;

import dev.cloudeko.zenei.domain.model.Token;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
import io.quarkiverse.mailpit.test.Mailbox;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.vertx.ext.mail.MailMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthResourceTest {

    @Inject
    MockMailbox mailbox;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void init() {
        mailbox.clear();
    }

    @Test
    @Order(1)
    @DisplayName("Create user via signup (POST /signup) should return (200 OK)")
    void testCreateUser() {
        final var request = new SignupRequest("test-user", "test@test.com", "test-password");

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

        verificationLink = verificationLink.replace("http://localhost:8080", "http://localhost:8081");

        given()
                .post(verificationLink)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
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
        final var token = given()
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
