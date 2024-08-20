package dev.cloudeko.zenei.extension.core.config;

import java.util.Optional;

public interface DefaultUserConfig {
    String email();
    String username();
    String password();
    Optional<String> role();
}
