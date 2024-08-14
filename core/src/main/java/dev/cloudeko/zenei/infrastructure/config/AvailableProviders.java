package dev.cloudeko.zenei.infrastructure.config;

public enum AvailableProviders {
    GITHUB("github"),
    DISCORD("discord");

    private final String providerName;

    AvailableProviders(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }
}
