package dev.cloudeko.zenei.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import lombok.Getter;

import java.util.Map;

public class MockServerResource implements QuarkusTestResourceLifecycleManager {

    @Getter
    private static WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer();
            wireMockServer.start();
        }

        return Map.of();
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }
}
