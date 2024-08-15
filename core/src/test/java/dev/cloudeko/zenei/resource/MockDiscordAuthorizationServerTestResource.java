package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.extension.external.web.external.discord.DiscordUser;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.Set;

public class MockDiscordAuthorizationServerTestResource extends AbstractMockAuthorizationServerTestResource {

    private static final DiscordUser DISCORD_USER = new DiscordUser("12345L", "discord-user", "Discord Test User", "1234",
            "https://example.com/avatar.jpg",
            "test@discord.com", true);

    @Override
    protected Map<String, String> providerSpecificStubsAndConfig(WireMockServer server) {
        try {
            // Mock the Discord user data endpoints
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/discord/users/@me"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                            .withBody(objectMapper.writeValueAsString(DISCORD_USER))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Map.of(
                "zenei.external.auth.providers.discord.base-uri", server.baseUrl() + "/discord",
                "zenei.external.auth.providers.discord.client-id", "mock_client_id",
                "zenei.external.auth.providers.discord.client-secret", "mock_client_secret",
                "zenei.external.auth.providers.discord.authorization-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.AUTHORIZE),
                "zenei.external.auth.providers.discord.token-uri",
                server.baseUrl() + getFeaturePath(TestProviderFeature.ACCESS_TOKEN),
                "zenei.external.auth.providers.discord.redirect-uri", getCallbackEndpoint(),
                "zenei.external.auth.providers.discord.scope", "user,email"
        );
    }

    @Override
    protected Set<TestProviderFeature> getFeatures() {
        return Set.of(TestProviderFeature.AUTHORIZE, TestProviderFeature.ACCESS_TOKEN);
    }

    @Override
    protected String getProvider() {
        return "discord";
    }
}
