package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
import dev.cloudeko.zenei.domain.feature.CreateUser;
import dev.cloudeko.zenei.domain.feature.LoginUserWithPassword;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
    private final LoginUserWithPassword loginUserWithPassword;

    @POST
    @Transactional
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@Valid SignupRequest request) {
        final var user = createUser.handle(request.toCreateUserInput());
        return Response.ok(user).build();
    }

    @POST
    @Path("/token")
    public Response token(@QueryParam("grant_type") String grantType,
            @QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("refresh_token") String refreshToken) {
        switch (grantType) {
            case "password": {
                final var token = loginUserWithPassword.handle(new LoginPasswordInput(username, password));
                return Response.ok(token).build();
            }
            case "refresh_token": {

            }
            default:
                return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
