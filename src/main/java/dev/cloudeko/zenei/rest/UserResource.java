package dev.cloudeko.zenei.rest;

import dev.cloudeko.model.app.User;
import dev.cloudeko.model.app.UserRole;
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

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> signup(
            @FormParam("username") String username,
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        return userService.createUser(username, email, password, UserRole.USER)
                .onItem().ifNotNull().transform(user -> Response.ok().entity(user).build())
                .onItem().ifNull().continueWith(Response.status(Response.Status.BAD_REQUEST).build());
    }

    @GET
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Web app retrieved successfully", content = @Content(schema = @Schema(implementation = User.class)))
    public Uni<Response> getCurrentUserInfo() {
        return identity.getDeferredIdentity()
                .onItem().ifNotNull().transformToUni(securityIdentity -> userService.getUserByName(securityIdentity.getPrincipal().getName()))
                .onItem().ifNotNull().transform(user -> Response.ok().entity(user).build())
                .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND).build());
    }
}
