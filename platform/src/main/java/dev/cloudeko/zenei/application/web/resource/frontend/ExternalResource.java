package dev.cloudeko.zenei.application.web.resource.frontend;

import dev.cloudeko.zenei.application.web.model.response.SessionTokenResponse;
import dev.cloudeko.zenei.extension.core.feature.LoginUserWithAuthorizationCode;
import dev.cloudeko.zenei.extension.external.ExternalAuthProvider;
import dev.cloudeko.zenei.extension.external.ExternalAuthResolver;
import dev.cloudeko.zenei.extension.external.providers.AvailableProvider;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;

@Path("/external")
@AllArgsConstructor
@Tag(name = "External Services", description = "Endpoints for external authentication (OAuth)")
public class ExternalResource {

    private final ExternalAuthResolver externalAuthResolver;

    private final LoginUserWithAuthorizationCode loginUserWithAuthorizationCode;

    @GET
    @Path("/login/{provider}")
    public Response login(@PathParam("provider") String provider) {
        final var providerConfig = getProviderConfig(provider);

        if (providerConfig.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var uriBuilder = UriBuilder.fromUri(providerConfig.get().getAuthorizationEndpoint())
                .queryParam("client_id", providerConfig.get().config().clientId())
                .queryParam("redirect_uri", AvailableProvider.getProvider(provider).getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", AvailableProvider.getProvider(provider).getScope());

        return Response.temporaryRedirect(uriBuilder.build()).build();
    }

    @GET
    @Transactional
    @Path("/callback/{provider}")
    public Response callback(@PathParam("provider") String provider,
            @QueryParam("code") String code,
            @QueryParam("state") String state) {
        final var providerConfig = getProviderConfig(provider);

        if (providerConfig.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var token = loginUserWithAuthorizationCode.handle(provider, code);

        return Response.ok(new SessionTokenResponse(token)).build();
    }

    private Optional<ExternalAuthProvider> getProviderConfig(String provider) {
        return externalAuthResolver.getAuthProvider(provider);
    }
}
