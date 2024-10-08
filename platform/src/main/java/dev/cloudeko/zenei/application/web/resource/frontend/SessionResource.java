package dev.cloudeko.zenei.application.web.resource.frontend;

import dev.cloudeko.zenei.application.web.model.response.SessionTokenResponse;
import dev.cloudeko.zenei.extension.core.config.ApplicationConfig;
import dev.cloudeko.zenei.extension.core.feature.RefreshAccessToken;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/frontend/session")
@AllArgsConstructor
@Tag(name = "Email Password Service", description = "API for user authentication with email and password")
public class SessionResource {

    private final ApplicationConfig applicationConfig;
    private final RefreshAccessToken refreshAccessToken;

    @POST
    @Transactional
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(@QueryParam("refresh_token") String refreshToken) {
        final var token = refreshAccessToken.handle(refreshToken);
        return Response.ok(new SessionTokenResponse(token)).build();
    }
}
