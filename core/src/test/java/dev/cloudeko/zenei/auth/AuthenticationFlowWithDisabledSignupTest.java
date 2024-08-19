package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.profile.RestrictedAuthenticationProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestProfile(RestrictedAuthenticationProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationFlowWithDisabledSignupTest {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Create user via email and password (POST /user) should return (403 FORBIDDEN)")
    void testCreateUser() {
        given()
                .formParam("username", "test-user2")
                .formParam("email", "test@test.com")
                .formParam("password", "test-password")
                .formParam("strategy", "PASSWORD")
                .post("/frontend/register")
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }
}
