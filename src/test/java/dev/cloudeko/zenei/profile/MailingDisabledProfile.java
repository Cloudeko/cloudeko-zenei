package dev.cloudeko.zenei.profile;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class MailingDisabledProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("quarkus.mailer.mock", "false", "zenei.mailer.auto-confirm", "true");
    }
}
