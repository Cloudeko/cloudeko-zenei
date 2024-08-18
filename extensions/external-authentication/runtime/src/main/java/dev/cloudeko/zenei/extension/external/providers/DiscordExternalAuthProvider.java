package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.endpoint.ProviderEndpoints;
import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.discord.DiscordClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;

import java.net.URI;
import java.util.List;

public record DiscordExternalAuthProvider(ExternalAuthProviderConfig config) implements ExternalAuthProvider {

    @Override
    public ExternalUserProfile getExternalUserProfile(ExternalProviderAccessToken accessToken) {
        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(getBaseEndpoint()))
                .build(DiscordClient.class);

        final var user = client.getCurrentlyLoggedInUser("Bearer " + accessToken.getAccessToken());

        // Avatar URL handling
        String avatarUrl;
        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            final var discriminator = Integer.parseInt(user.getDiscriminator());
            avatarUrl = String.format("https://cdn.discordapp.com/embed/avatars/%d.png", discriminator % 5);
        } else {
            final var extension = user.getAvatar().startsWith("a_") ? "gif" : "png";
            avatarUrl = String.format("https://cdn.discordapp.com/avatars/%s/%s.%s", user.getId(), user.getAvatar(), extension);
        }

        return ExternalUserProfile.builder()
                .id(user.getId())
                .username(String.format("%s#%s", user.getUsername(), user.getDiscriminator()))
                .avatarUrl(avatarUrl)
                .emails(List.of(new ExternalUserProfile.ExternalUserEmail(user.getEmail(), true, user.isVerified())))
                .build();
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
