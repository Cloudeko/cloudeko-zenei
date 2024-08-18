package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.application.web.model.response.ExternalAccessTokensResponse;
import dev.cloudeko.zenei.application.web.model.response.PrivateUsersResponse;
import dev.cloudeko.zenei.extension.external.providers.AvailableProvider;
import dev.cloudeko.zenei.resource.MockDiscordAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockGithubAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockGoogleAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockServerResource;
import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithTestResource.List({
        @WithTestResource(MockServerResource.class),
        @WithTestResource(MockGithubAuthorizationServerTestResource.class),
        @WithTestResource(MockDiscordAuthorizationServerTestResource.class),
        @WithTestResource(MockGoogleAuthorizationServerTestResource.class)
})
public class AuthenticationFlowWithExternalProviderTest {

    private static final String[] IGNORED_PROVIDERS = { null };

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Order(1)
    @MethodSource("createProviderData")
    @ParameterizedTest(name = "Test Case for provider: {0}")
    @DisplayName("Retrieve a access token using authorization (GET /external/login/<PROVIDER>) should return (200 OK)")
    void testGetUserInfo(String provider) {
        given().get("/external/login/" + provider)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        "access_token", notNullValue(),
                        "refresh_token", notNullValue()
                );
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = "admin")
    @DisplayName("Retrieve a access token using authorization (GET /admin/users/{userId}/external_access_tokens/{provider}) should return (200 OK)")
    void testGetExternalAccessToken() {
        final var users = given()
                .get("/admin/users")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(PrivateUsersResponse.class);

        assertEquals(createProviderData().count(), users.size());

        users.forEach(user -> {
            final var tokens = given()
                    .get("/admin/users/" + user.getId() + "/external_access_tokens/" + user.getUsername()
                            .substring(0, user.getUsername().indexOf("-")))

                    .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .extract().as(ExternalAccessTokensResponse.class);

            assertEquals(1, tokens.size());
        });
    }

    static Stream<Arguments> createProviderData() {
        return Stream.of(AvailableProvider.values())
                .filter(provider -> !isIgnoredProvider(provider))
                .map(provider -> Arguments.of(provider.name()));
    }

    private static boolean isIgnoredProvider(AvailableProvider provider) {
        for (String ignoredProvider : IGNORED_PROVIDERS) {
            if (provider.name().equalsIgnoreCase(ignoredProvider)) {
                return true;
            }
        }
        return false;
    }
}
