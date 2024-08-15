package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import lombok.Getter;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.Arrays;
import java.util.function.Function;

public enum AvailableProvider {
    GITHUB("github", GithubExternalAuthProvider::new),
    DISCORD("discord", DiscordExternalAuthProvider::new),
    GOOGLE("google", GoogleExternalAuthProvider::new);

    @Getter
    private final String providerName;
    private final Function<ExternalAuthProviderConfig, ExternalAuthProvider> provider;

    AvailableProvider(String providerName, Function<ExternalAuthProviderConfig, ExternalAuthProvider> provider) {
        this.providerName = providerName;
        this.provider = provider;
    }

    public static AvailableProvider getProvider(String providerName) {
        return Arrays.stream(values())
                .filter(provider -> provider.getProviderName().equalsIgnoreCase(providerName))
                .findFirst()
                .orElse(null);
    }

    public static ExternalAuthProvider getProvider(String providerName, ExternalAuthProviderConfig config) {
        return getProvider(providerName).getProvider(config);
    }

    public ExternalAuthProvider getProvider(ExternalAuthProviderConfig config) {
        return provider.apply(config);
    }

    public String getRedirectUri() {
        return ConfigProvider.getConfig()
                .getOptionalValue("zenei.external.auth." + providerName + ".redirect-uri", String.class)
                .orElse("http://localhost:8080/external/callback/" + providerName);
    }
}
