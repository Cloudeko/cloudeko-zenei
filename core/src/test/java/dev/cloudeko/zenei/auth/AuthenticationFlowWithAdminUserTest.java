package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.application.web.model.response.TokenResponse;
import dev.cloudeko.zenei.profile.DefaultAdminUserProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@TestProfile(DefaultAdminUserProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationFlowWithAdminUserTest {

    @Test
    @DisplayName("Retrieve all users (GET /admin/users) should return (200 OK)")
    void testGetUserInfo() {
        final var token = given()
                .queryParam("grant_type", "password")
                .queryParam("username", "admin@test.com")
                .queryParam("password", "test")
                .post("/user/token")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(TokenResponse.class);

        given()
                .header("Authorization", "Bearer " + token.getAccessToken())
                .get("/admin/users")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "users.size()", greaterThanOrEqualTo(1),
                        "users.getFirst().username", equalTo("admin"),
                        "users.getFirst().primary_email_address", equalTo("admin@test.com")
                );
    }
}
