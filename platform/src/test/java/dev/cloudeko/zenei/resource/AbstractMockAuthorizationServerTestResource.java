package dev.cloudeko.zenei.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Map;
import java.util.Set;

public abstract class AbstractMockAuthorizationServerTestResource implements QuarkusTestResourceLifecycleManager {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> start() {
        final var server = MockServerResource.getWireMockServer();

        try {
            if (isFeatureEnabled(TestProviderFeature.AUTHORIZE)) {
                // Mock the authorization URL
                server.stubFor(WireMock.get(WireMock.urlPathEqualTo(getFeaturePath(TestProviderFeature.AUTHORIZE)))
                        .withQueryParam("client_id", WireMock.matching(".*"))
                        .withQueryParam("redirect_uri", WireMock.matching(".*"))
                        .withQueryParam("scope", WireMock.matching(".*"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(Response.Status.FOUND.getStatusCode())
                                .withHeader("Location", getCallbackEndpoint() + "?code=mock_code&state=mock_state")));
            }

            if (isFeatureEnabled(TestProviderFeature.ACCESS_TOKEN)) {
                // Mock the access token endpoint
                server.stubFor(WireMock.post(WireMock.urlPathEqualTo(getFeaturePath(TestProviderFeature.ACCESS_TOKEN)))
                        .withFormParam("client_id", WireMock.matching(".*"))
                        .withFormParam("client_secret", WireMock.matching(".*"))
                        .withFormParam("code", WireMock.matching(".*"))
                        .withFormParam("grant_type", WireMock.matching(".*"))
                        .withFormParam("redirect_uri", WireMock.matching(".*"))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                                .withBody(objectMapper.writeValueAsString(
                                        getFeatureResponse(TestProviderFeature.ACCESS_TOKEN)))));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return providerSpecificStubsAndConfig(server);
    }

    @Override
    public void stop() {
    }

    protected int getTestPort() {
        return ConfigProvider.getConfig().getOptionalValue("quarkus.http.test-port", Integer.class).orElse(8081);
    }

    protected boolean isFeatureEnabled(TestProviderFeature feature) {
        return getFeatures().contains(feature);
    }

    protected String getFeaturePath(TestProviderFeature feature) {
        return getPrefix() + feature.getPath();
    }

    protected Object getFeatureResponse(TestProviderFeature feature) {
        return feature.getResponse();
    }

    protected abstract Map<String, String> providerSpecificStubsAndConfig(WireMockServer server);

    protected abstract Set<TestProviderFeature> getFeatures();

    protected abstract String getProvider();

    protected String getPrefix() {
        return String.format("/%s", getProvider());
    }

    protected String getCallbackEndpoint() {
        return "http://localhost:" + getTestPort() + "/external/callback/" + getProvider();
    }
}
