package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.extension.external.web.external.google.GoogleUser;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.Set;

public class MockGoogleAuthorizationServerTestResource extends AbstractMockAuthorizationServerTestResource {

    private static final GoogleUser GOOGLE_USER = new GoogleUser("12345L", "google-user", "https://example.com/avatar.jpg",
            "test@google.com", true);

    @Override
    protected Map<String, String> providerSpecificStubsAndConfig(WireMockServer server) {
        try {
            // Mock the user data endpoints
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/google/userinfo/v2/me"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                            .withBody(objectMapper.writeValueAsString(GOOGLE_USER))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Map.of(
                "zenei.external.auth.providers.google.base-uri", server.baseUrl() + "/google",
                "zenei.external.auth.providers.google.client-id", "mock_client_id",
                "zenei.external.auth.providers.google.client-secret", "mock_client_secret",
                "zenei.external.auth.providers.google.authorization-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.AUTHORIZE),
                "zenei.external.auth.providers.google.token-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.ACCESS_TOKEN),
                "zenei.external.auth.providers.google.redirect-uri", getCallbackEndpoint(),
                "zenei.external.auth.providers.google.scope", "user,email"
        );
    }

    @Override
    protected Set<TestProviderFeature> getFeatures() {
        return Set.of(TestProviderFeature.AUTHORIZE, TestProviderFeature.ACCESS_TOKEN);
    }

    @Override
    protected String getProvider() {
        return "google";
    }
}

