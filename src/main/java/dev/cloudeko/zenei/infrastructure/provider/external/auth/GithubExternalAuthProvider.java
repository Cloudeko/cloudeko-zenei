package dev.cloudeko.zenei.infrastructure.provider.external.auth;

import dev.cloudeko.zenei.domain.model.external.AccessToken;
import dev.cloudeko.zenei.domain.model.external.ExternalUserProfile;
import dev.cloudeko.zenei.domain.provider.ExternalAuthProvider;
import dev.cloudeko.zenei.domain.web.external.GithubApiClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import lombok.AllArgsConstructor;

import java.net.URI;

@AllArgsConstructor
public class GithubExternalAuthProvider implements ExternalAuthProvider {

    private final String api;

    @Override
    public ExternalUserProfile getExternalUserProfile(AccessToken accessToken) {
        final var client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(api))
                .build(GithubApiClient.class);

        final var externalUserBuilder = ExternalUserProfile.builder();
        final var user = client.getCurrentlyLoggedInUser("Bearer " + accessToken.getAccessToken());

        externalUserBuilder.id(user.getId().toString());
        externalUserBuilder.username(user.getLogin());
        externalUserBuilder.avatarUrl(user.getAvatarUrl());

        final var emails = client.getUserEmails("Bearer " + accessToken.getAccessToken());
        final var externalUserEmails = emails.stream()
                .map(email -> new ExternalUserProfile.ExternalUserEmail(
                        email.getEmail(),
                        email.isPrimary(),
                        email.isVerified()))
                .toList();

        externalUserBuilder.emails(externalUserEmails);

        return externalUserBuilder.build();
    }
}
