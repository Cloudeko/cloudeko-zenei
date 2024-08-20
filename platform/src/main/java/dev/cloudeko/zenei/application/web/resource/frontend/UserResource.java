package dev.cloudeko.zenei.application.web.resource.frontend;

import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.domain.feature.FindUserByIdentifier;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/frontend/user")
@AllArgsConstructor
@Tag(name = "User Service", description = "API for user authentication")
public class UserResource {

    private final ApplicationConfig applicationConfig;
    private final FindUserByIdentifier findUserByIdentifier;

    private final SecurityIdentity securityIdentity;

    @GET
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentUserInfo(@Context SecurityContext securityContext) {
        final var userId = Long.parseLong(securityContext.getUserPrincipal().getName());
        final var user = findUserByIdentifier.handle(userId);

        return Response.ok(new PrivateUserResponse(user)).build();
    }
}
