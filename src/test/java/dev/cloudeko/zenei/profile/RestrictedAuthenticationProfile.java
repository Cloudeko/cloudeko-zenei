package dev.cloudeko.zenei.profile;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class RestrictedAuthenticationProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "zenei.auth.sign-up.enabled", "false",
                "zenei.user.default.admin.username", "admin",
                "zenei.user.default.admin.email", "admin@test.com",
                "zenei.user.default.admin.password", "test",
                "zenei.user.default.admin.role", "admin"
        );
    }
}
