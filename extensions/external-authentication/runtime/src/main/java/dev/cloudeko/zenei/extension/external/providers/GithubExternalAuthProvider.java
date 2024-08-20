package dev.cloudeko.zenei.extension.external.providers;

import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalUserProfile;
import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.endpoint.ProviderEndpoints;
import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubClient;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUser;
import dev.cloudeko.zenei.extension.external.web.external.github.GithubUserEmail;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;

import java.net.URI;
import java.util.List;

public record GithubExternalAuthProvider(ExternalAuthProviderConfig config) implements ExternalAuthProvider {

    @Override
    public ExternalUserProfile getExternalUserProfile(ExternalProviderAccessToken accessToken) {
        GithubClient client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(getBaseEndpoint()))
                .build(GithubClient.class);

        ExternalUserProfile.ExternalUserProfileBuilder externalUserBuilder = ExternalUserProfile.builder();
        GithubUser user = client.getCurrentlyLoggedInUser("Bearer " + accessToken.getAccessToken());

        externalUserBuilder.id(user.getId().toString());
        externalUserBuilder.username(user.getLogin());
        externalUserBuilder.avatarUrl(user.getAvatarUrl());

        List<GithubUserEmail> emails = client.getUserEmails("Bearer " + accessToken.getAccessToken());
        List<ExternalUserProfile.ExternalUserEmail> externalUserEmails = emails.stream()
                .map(email -> new ExternalUserProfile.ExternalUserEmail(
                        email.getEmail(),
                        email.isPrimary(),
                        email.isVerified()))
                .toList();

        externalUserBuilder.emails(externalUserEmails);

        return externalUserBuilder.build();
    }

    @Override
    public String getAuthorizationEndpoint() {
        return config.authorizationUri().orElse(ProviderEndpoints.GITHUB.getAuthorizationEndpoint());
    }

    @Override
    public String getTokenEndpoint() {
        return config.tokenUri().orElse(ProviderEndpoints.GITHUB.getTokenEndpoint());
    }

    @Override
    public String getBaseEndpoint() {
        return config.baseUri().orElse(ProviderEndpoints.GITHUB.getBaseEndpoint());
    }
}
