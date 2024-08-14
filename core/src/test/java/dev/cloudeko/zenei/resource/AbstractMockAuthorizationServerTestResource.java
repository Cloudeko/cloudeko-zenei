package dev.cloudeko.zenei.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public abstract class AbstractMockAuthorizationServerTestResource implements QuarkusTestResourceLifecycleManager {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> start() {
        WireMockServer wireMockServer = MockServerResource.getWireMockServer();
        return providerSpecificStubsAndConfig(wireMockServer);
    }

    @Override
    public void stop() {
    }

    protected abstract Map<String, String> providerSpecificStubsAndConfig(WireMockServer server);
}
