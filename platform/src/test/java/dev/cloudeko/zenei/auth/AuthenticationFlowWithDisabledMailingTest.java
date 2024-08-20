package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.application.web.model.response.SessionTokenResponse;
import dev.cloudeko.zenei.profile.MailingDisabledProfile;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestProfile(MailingDisabledProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationFlowWithDisabledMailingTest {

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

        assertEquals(0, mailbox.getTotalMessagesSent());
    }

    @Test
    @Order(2)
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
}
