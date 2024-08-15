package dev.cloudeko.zenei.auth;

import dev.cloudeko.zenei.extension.external.providers.AvailableProvider;
import dev.cloudeko.zenei.resource.MockDiscordAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockGithubAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockGoogleAuthorizationServerTestResource;
import dev.cloudeko.zenei.resource.MockServerResource;
import io.quarkus.test.common.WithTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
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
