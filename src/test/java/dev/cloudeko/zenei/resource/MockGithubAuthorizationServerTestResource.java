package dev.cloudeko.zenei.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.domain.model.external.AccessToken;
import dev.cloudeko.zenei.domain.model.external.provider.github.GithubUser;
import dev.cloudeko.zenei.domain.model.external.provider.github.GithubUserEmail;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MockGithubAuthorizationServerTestResource implements QuarkusTestResourceLifecycleManager {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        final var testPort = ConfigProvider.getConfig().getOptionalValue("quarkus.http.test-port", Integer.class).orElse(8081);

        try {
            // Mock the GitHub authorization URL
            wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/login/oauth/authorize"))
                    .withQueryParam("client_id", WireMock.matching(".*"))
                    .withQueryParam("redirect_uri", WireMock.matching(".*"))
                    .withQueryParam("scope", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withStatus(Response.Status.FOUND.getStatusCode())
                            .withHeader("Location",
                                    "http://localhost:" + testPort + "/oauth/callback/github?code=mock_code&state=mock_state")));

            // Mock the access token endpoint
            wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo("/login/oauth/access_token"))
                    .withQueryParam("client_id", WireMock.matching(".*"))
                    .withQueryParam("client_secret", WireMock.matching(".*"))
                    .withQueryParam("code", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new AccessToken("mock_access_token", 3600L, "mock_refresh_token", "user,email", "bearer")
                            ))));

            // Mock the GitHub user data endpoints
            wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new GithubUser(12345L, "testuser", "Test User", "https://example.com/avatar.jpg",
                                            "testuser@example.com")
                            ))));

            wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user/emails"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    List.of(
                                            new GithubUserEmail("testuser@example.com", true, true),
                                            new GithubUserEmail("secondary@example.com", false, true)
                                    )
                            ))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Map.of(
                "zenei.oauth.providers.github.base-uri", wireMockServer.baseUrl(),
                "zenei.oauth.providers.github.client-id", "mock_client_id",
                "zenei.oauth.providers.github.client-secret", "mock_client_secret",
                "zenei.oauth.providers.github.authorization-uri", wireMockServer.baseUrl() + "/login/oauth/authorize",
                "zenei.oauth.providers.github.token-uri", wireMockServer.baseUrl() + "/login/oauth/access_token",
                "zenei.oauth.providers.github.redirect-uri", "http://localhost:8081/oauth/callback/github",
                "zenei.oauth.providers.github.scope", "user,email"
        );
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}
