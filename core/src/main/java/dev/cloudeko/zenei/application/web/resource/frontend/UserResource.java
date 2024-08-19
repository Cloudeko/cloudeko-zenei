package dev.cloudeko.zenei.application.web.resource.frontend;

import dev.cloudeko.zenei.application.web.model.request.RegisterRequest;
import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.application.web.model.response.TokenResponse;
import dev.cloudeko.zenei.domain.feature.*;
import dev.cloudeko.zenei.domain.model.email.EmailAddressInput;
import dev.cloudeko.zenei.domain.model.email.VerifyMagicLinkInput;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Path("/frontend/user")
@AllArgsConstructor
@Tag(name = "User Service", description = "API for user authentication")
public class UserResource {

    private final ApplicationConfig applicationConfig;
    private final FindUserByIdentifier findUserByIdentifier;

    @GET
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentUserInfo(@Context SecurityContext securityContext) {
        final var userId = Long.parseLong(securityContext.getUserPrincipal().getName());
        final var user = findUserByIdentifier.handle(userId);

        return Response.ok(new PrivateUserResponse(user)).build();
    }
}
