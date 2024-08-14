package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.extension.external.web.client.ExternalAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUser;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUserEmail;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.List;
import java.util.Map;

public class MockGithubAuthorizationServerTestResource extends AbstractMockAuthorizationServerTestResource {

    @Override
    protected Map<String, String> providerSpecificStubsAndConfig(WireMockServer server) {
        final var testPort = ConfigProvider.getConfig().getOptionalValue("quarkus.http.test-port", Integer.class).orElse(8081);

        try {
            // Mock the GitHub authorization URL
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/github/login/oauth/authorize"))
                    .withQueryParam("client_id", WireMock.matching(".*"))
                    .withQueryParam("redirect_uri", WireMock.matching(".*"))
                    .withQueryParam("scope", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withStatus(Response.Status.FOUND.getStatusCode())
                            .withHeader("Location",
                                    "http://localhost:" + testPort + "/external/callback/github?code=mock_code&state=mock_state")));

            // Mock the access token endpoint
            server.stubFor(WireMock.post(WireMock.urlPathEqualTo("/github/login/oauth/access_token"))
                    .withQueryParam("client_id", WireMock.matching(".*"))
                    .withQueryParam("client_secret", WireMock.matching(".*"))
                    .withQueryParam("code", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new ExternalAccessToken("mock_access_token", 3600L, "mock_refresh_token", "user,email",
                                            "bearer")
                            ))));

            // Mock the GitHub user data endpoints
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/github/user"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new GithubUser(12345L, "github-user", "Github Test User", "https://example.com/avatar.jpg",
                                            "test@github.com")
                            ))));

            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/github/user/emails"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    List.of(
                                            new GithubUserEmail("test@github.com", true, true),
                                            new GithubUserEmail("secondary@github.com", false, true)
                                    )
                            ))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Map.of(
                "zenei.external.auth.providers.github.base-uri", server.baseUrl() + "/github",
                "zenei.external.auth.providers.github.client-id", "mock_client_id",
                "zenei.external.auth.providers.github.client-secret", "mock_client_secret",
                "zenei.external.auth.providers.github.authorization-uri", server.baseUrl() + "/github/login/oauth/authorize",
                "zenei.external.auth.providers.github.token-uri", server.baseUrl() + "/github/login/oauth/access_token",
                "zenei.external.auth.providers.github.redirect-uri", "http://localhost:8081/external/callback/github",
                "zenei.external.auth.providers.github.scope", "user,email"
        );
    }
}
