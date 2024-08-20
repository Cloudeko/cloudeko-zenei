package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUser;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUserEmail;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockGithubAuthorizationServerTestResource extends AbstractMockAuthorizationServerTestResource {

    @Override
    protected Map<String, String> providerSpecificStubsAndConfig(WireMockServer server) {
        try {
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
                "zenei.external.auth.providers.github.authorization-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.AUTHORIZE),
                "zenei.external.auth.providers.github.token-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.ACCESS_TOKEN),
                "zenei.external.auth.providers.github.redirect-uri", getCallbackEndpoint(),
                "zenei.external.auth.providers.github.scope", "user,email"
        );
    }

    @Override
    protected Set<TestProviderFeature> getFeatures() {
        return Set.of(TestProviderFeature.AUTHORIZE, TestProviderFeature.ACCESS_TOKEN);
    }

    @Override
    protected String getProvider() {
        return "github";
    }
}
