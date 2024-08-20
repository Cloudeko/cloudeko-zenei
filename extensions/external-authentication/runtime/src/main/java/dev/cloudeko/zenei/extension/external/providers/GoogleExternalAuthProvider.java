package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.endpoint.ProviderEndpoints;
import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.BaseExternalClient;
import dev.cloudeko.zenei.extension.external.web.external.google.GoogleClient;
import dev.cloudeko.zenei.extension.external.web.external.google.GoogleUser;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;

import java.net.URI;
import java.util.List;

public record GoogleExternalAuthProvider(ExternalAuthProviderConfig config) implements ExternalAuthProvider {

    @Override
    public ExternalUserProfile getExternalUserProfile(ExternalProviderAccessToken accessToken) {
        BaseExternalClient<GoogleUser> client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(getBaseEndpoint()))
                .build(GoogleClient.class);

        GoogleUser user = client.getCurrentlyLoggedInUser("Bearer " + accessToken.getAccessToken());

        return ExternalUserProfile.builder()
                .id(user.getId())
                .username(user.getName())
                .avatarUrl(user.getPicture())
                .emails(List.of(new ExternalUserProfile.ExternalUserEmail(
                        user.getEmail(),
                        true,
                        user.isEmailVerified())))
                .build();
    }

    @Override
    public String getAuthorizationEndpoint() {
        return config.authorizationUri().orElse(ProviderEndpoints.GOOGLE.getAuthorizationEndpoint());
    }

    @Override
    public String getTokenEndpoint() {
        return config.tokenUri().orElse(ProviderEndpoints.GOOGLE.getTokenEndpoint());
    }

    @Override
    public String getBaseEndpoint() {
        return config.baseUri().orElse(ProviderEndpoints.GOOGLE.getBaseEndpoint());
    }
}
