package dev.cloudeko.zenei.resource;

import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;
import lombok.Getter;

@Getter
public enum TestProviderFeature {

    AUTHORIZE("/login/oauth/authorize"),
    ACCESS_TOKEN("/login/oauth/access_token", new ExternalProviderAccessToken("mock_access_token",
            3600L,
            "mock_refresh_token",
            "user,email",
            "bearer"));

    private final String path;
    private final Object response;

    TestProviderFeature(String path) {
        this.path = path;
        this.response = null;
    }

    TestProviderFeature(String path, Object response) {
        this.path = path;
        this.response = response;
    }
}
