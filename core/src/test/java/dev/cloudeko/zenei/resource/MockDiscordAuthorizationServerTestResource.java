package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.cloudeko.zenei.extension.external.web.client.ExternalAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.discord.DiscordUser;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Map;

public class MockDiscordAuthorizationServerTestResource extends AbstractMockAuthorizationServerTestResource {

    @Override
    protected Map<String, String> providerSpecificStubsAndConfig(WireMockServer server) {
        final var testPort = ConfigProvider.getConfig().getOptionalValue("quarkus.http.test-port", Integer.class).orElse(8081);

        try {
            // Mock the Discord authorization URL
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/discord/login/oauth/authorize"))
                    .withQueryParam("client_id", WireMock.matching(".*"))
                    .withQueryParam("redirect_uri", WireMock.matching(".*"))
                    .withQueryParam("scope", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withStatus(Response.Status.FOUND.getStatusCode())
                            .withHeader("Location",
                                    "http://localhost:" + testPort + "/external/callback/discord?code=mock_code&state=mock_state")));

            // Mock the Discord access token endpoint
            server.stubFor(WireMock.post(WireMock.urlPathEqualTo("/discord/login/oauth/access_token"))
                    .withFormParam("client_id", WireMock.matching(".*"))
                    .withFormParam("client_secret", WireMock.matching(".*"))
                    .withFormParam("code", WireMock.matching(".*"))
                    .withFormParam("grant_type", WireMock.matching(".*"))
                    .withFormParam("redirect_uri", WireMock.matching(".*"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new ExternalAccessToken("mock_access_token", 3600L, "mock_refresh_token", "user,email",
                                            "bearer")
                            ))));

            // Mock the Discord user data endpoints
            server.stubFor(WireMock.get(WireMock.urlPathEqualTo("/discord/users/@me"))
                    .withHeader("Authorization", WireMock.equalTo("Bearer mock_access_token"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(
                                    new DiscordUser("12345L", "discord-user", "Discord Test User", "1234", "https://example.com/avatar.jpg",
                                            "test@discord.com", true)
                            ))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Map.of(
                "zenei.external.auth.providers.discord.base-uri", server.baseUrl() + "/discord",
                "zenei.external.auth.providers.discord.client-id", "mock_client_id",
                "zenei.external.auth.providers.discord.client-secret", "mock_client_secret",
                "zenei.external.auth.providers.discord.authorization-uri",
                server.baseUrl() + "/discord/login/oauth/authorize",
                "zenei.external.auth.providers.discord.token-uri",
                server.baseUrl() + "/discord/login/oauth/access_token",
                "zenei.external.auth.providers.discord.redirect-uri", "http://localhost:8081/external/callback/discord",
                "zenei.external.auth.providers.discord.scope", "user,email"
        );
    }
}
