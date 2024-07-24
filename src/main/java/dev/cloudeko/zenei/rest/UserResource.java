package dev.cloudeko.zenei.rest;

import dev.cloudeko.zenei.dto.User;
import dev.cloudeko.zenei.service.UserService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.CurrentIdentityAssociation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/users")
@Tag(name = "User Service", description = "API for managing users")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    CurrentIdentityAssociation identity;

    @GET
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Web app retrieved successfully", content = @Content(schema = @Schema(implementation = User.class)))
    public Uni<Response> getCurrentUserInfo() {
        return identity.getDeferredIdentity()
                .onItem().ifNotNull()
                .transformToUni(securityIdentity -> userService.getUserByName(securityIdentity.getPrincipal().getName()))
                .onItem().ifNotNull().transform(user -> Response.ok().entity(user).build())
                .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND).build());
    }
}
