package dev.cloudeko.zenei;

import dev.cloudeko.zenei.dto.User;
import dev.cloudeko.zenei.rest.request.CreateUserRequest;
import dev.cloudeko.zenei.rest.request.SignupRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        assertNotNull(user);
    }
}
