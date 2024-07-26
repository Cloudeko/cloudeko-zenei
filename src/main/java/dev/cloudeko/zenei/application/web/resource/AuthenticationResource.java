package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
import dev.cloudeko.zenei.domain.feature.CreateUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication Service", description = "API for user authentication")
public class AuthenticationResource {

    private final CreateUser createUser;

    @POST
    @Transactional
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@Valid SignupRequest request) {
        final var user = createUser.handle(request.toCreateUserInput());
        return Response.ok(user).status(Response.Status.CREATED).build();
    }

    /*@POST
    @Path("/token")
    public Uni<Response> token(@QueryParam("grant_type") String grantType,
            @QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("refresh_token") String refreshToken) {
        return switch (grantType) {
            case "password" -> authenticationService.login(username, password)
                    .onItem().ifNotNull().transform(token -> Response.ok().entity(token).build())
                    .onItem().ifNull().continueWith(Response.status(Response.Status.UNAUTHORIZED).build());
            case "refresh_token" -> authenticationService.refreshToken(refreshToken)
                    .onItem().ifNotNull().transform(token -> Response.ok().entity(token).build())
                    .onItem().ifNull().continueWith(Response.status(Response.Status.UNAUTHORIZED).build());
            default -> Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
        };
    }*/
}
