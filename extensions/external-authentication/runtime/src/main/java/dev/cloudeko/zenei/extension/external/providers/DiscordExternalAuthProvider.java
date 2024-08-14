package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.endpoint.ProviderEndpoints;
import dev.cloudeko.zenei.extension.external.web.client.ExternalAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.discord.DiscordApiClient;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubApiClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;

import java.net.URI;
import java.util.List;

public record DiscordExternalAuthProvider(ExternalAuthProviderConfig config) implements ExternalAuthProvider {

    @Override
    public ExternalUserProfile getExternalUserProfile(ExternalAccessToken accessToken) {
        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(getBaseEndpoint()))
                .build(DiscordApiClient.class);

        final var externalUserBuilder = ExternalUserProfile.builder();
        final var user = client.getCurrentlyLoggedInUser("Bearer " + accessToken.getAccessToken());

        externalUserBuilder.id(user.getId());
        externalUserBuilder.username(user.getUsername());
        externalUserBuilder.avatarUrl(user.getAvatar());
        externalUserBuilder.emails(
                List.of(new ExternalUserProfile.ExternalUserEmail(user.getEmail(), true, user.isVerified())));

        return externalUserBuilder.build();
    }

    @Override
    public String getAuthorizationEndpoint() {
        return config.authorizationUri().orElse(ProviderEndpoints.DISCORD.getAuthorizationEndpoint());
    }

    @Override
    public String getTokenEndpoint() {
        return config.tokenUri().orElse(ProviderEndpoints.DISCORD.getTokenEndpoint());
    }

    @Override
    public String getBaseEndpoint() {
        return config.baseUri().orElse(ProviderEndpoints.DISCORD.getBaseEndpoint());
    }
}
