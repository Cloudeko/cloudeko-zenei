package dev.cloudeko.zenei.domain.provider;

public interface ExternalAuthenticationProvider {
    ExternalAuthProvider getAuthProvider(String providerName);
}
