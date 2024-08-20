package dev.cloudeko.zenei.application.web.resource.backend;

import dev.cloudeko.zenei.application.web.model.response.ExternalAccessTokenResponse;
import dev.cloudeko.zenei.application.web.model.response.ExternalAccessTokensResponse;
import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.application.web.model.response.PrivateUsersResponse;
import dev.cloudeko.zenei.extension.core.feature.ListExternalAccessTokens;
import dev.cloudeko.zenei.extension.core.feature.ListUsers;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@AllArgsConstructor
@RolesAllowed("admin")
@Path("/admin/users")
@Tag(name = "Admin Users Service", description = "API for managing users")
public class AdminUsersResource {

    private final ListUsers listUsers;
    private final ListExternalAccessTokens listExternalAccessTokens;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("page") Optional<Integer> page, @QueryParam("size") Optional<Integer> size) {
        final var users = listUsers.listUsers(page.orElse(0), size.orElse(100));
        final var usersResponse = users.stream().map(PrivateUserResponse::new).toList();

        return Response.ok(new PrivateUsersResponse(usersResponse)).build();
    }

    @GET
    @Path("{userId}/external_access_tokens/{provider}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExternalAccessToken(@PathParam("userId") Long userId, @PathParam("provider") String provider) {
        final var externalAccessTokens = listExternalAccessTokens.listByProvider(userId, provider);
        final var externalAccessTokensResponse = externalAccessTokens.stream().map(ExternalAccessTokenResponse::new).toList();

        return Response.ok(new ExternalAccessTokensResponse(externalAccessTokensResponse)).build();
    }
}
