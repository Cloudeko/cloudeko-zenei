package dev.cloudeko.zenei.application.web.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//@Path("/user")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User Service", description = "API for managing users")
public class UserResource {

    /*@Inject
    UserService userService;

    @Inject
    CurrentIdentityAssociation identity;

    @GET
    @Authenticated
    @APIResponse(responseCode = "200", description = "Web app retrieved successfully", content = @Content(schema = @Schema(implementation = User.class)))
    public Uni<Response> getCurrentUserInfo() {
        return identity.getDeferredIdentity()
                .onItem().ifNotNull()
                .transformToUni(securityIdentity -> userService.getUserByEmail(securityIdentity.getPrincipal().getName()))
                .onItem().ifNotNull().transform(user -> Response.ok().entity(user).build())
                .onItem().ifNull().continueWith(Response.status(Response.SigninStatus.NOT_FOUND).build());
    }*/
}
