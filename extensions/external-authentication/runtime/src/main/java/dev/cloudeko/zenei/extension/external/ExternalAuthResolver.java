package dev.cloudeko.zenei.extension.external;

import java.util.Optional;

public interface ExternalAuthResolver {
    Optional<ExternalAuthProvider> getAuthProvider(String providerName);
}
