package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
import dev.cloudeko.zenei.domain.feature.*;
import dev.cloudeko.zenei.domain.model.email.ConfirmEmailInput;
import dev.cloudeko.zenei.domain.model.email.EmailInput;
import dev.cloudeko.zenei.domain.model.token.LoginPasswordInput;
import dev.cloudeko.zenei.domain.model.token.RefreshTokenInput;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Path("/user")
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication Service", description = "API for user authentication")
public class AuthenticationResource {

    private final CreateUser createUser;
    private final VerifyEmail verifyEmail;
    private final RefreshAccessToken refreshAccessToken;
    private final CreateEmailAddress createEmailAddress;
    private final LoginUserWithPassword loginUserWithPassword;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response signup(@BeanParam @Valid SignupRequest request) {
        final var user = createUser.handle(request.toCreateUserInput());
        final var emailAddress = user.getEmailAddresses().stream().findFirst().orElseThrow();
        if (!createEmailAddress.handle(new EmailInput(emailAddress))) {
            return Response.serverError().build();
        }

        if (request.getRedirectTo() != null) {
            return Response.temporaryRedirect(request.getRedirectTo()).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Transactional
    @Path("/verify-email")
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response verifyEmail(@QueryParam("token") String token, @QueryParam("redirect_to") URI redirectTo) {
        verifyEmail.handle(new ConfirmEmailInput(token));
        if (redirectTo != null) {
            return Response.temporaryRedirect(redirectTo).build();
        }

        return Response.noContent().build();
    }

    @POST
    @Transactional
    @Path("/token")
    public Response token(@QueryParam("grant_type") String grantType,
            @QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("refresh_token") String refreshToken) {
        return switch (grantType) {
            case "password" -> {
                final var token = loginUserWithPassword.handle(new LoginPasswordInput(username, password));
                yield Response.ok(token).build();
            }
            case "refresh_token" -> {
                final var token = refreshAccessToken.handle(new RefreshTokenInput(refreshToken));
                yield Response.ok(token).build();
            }
            default -> Response.status(Response.Status.BAD_REQUEST).build();
        };
    }
}
