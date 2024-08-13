package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.request.SignupRequest;
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

@Path("/user")
@AllArgsConstructor
@Tag(name = "User Service", description = "API for user authentication")
public class UserResource {

    private final ApplicationConfig applicationConfig;

    private final CreateUser createUser;
    private final FindUserByIdentifier findUserByIdentifier;

    private final VerifyMagicLink verifyMagicLink;
    private final RefreshAccessToken refreshAccessToken;
    private final SendMagicLinkVerifyEmail sendMagicLinkVerifyEmail;

    private final LoginUserWithPassword loginUserWithPassword;
    private final LoginUserWithAuthorizationCode loginUserWithAuthorizationCode;

    @GET
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentUserInfo(@Context SecurityContext securityContext) {
        final var userId = Long.parseLong(securityContext.getUserPrincipal().getName());
        final var user = findUserByIdentifier.handle(userId);

        return Response.ok(new PrivateUserResponse(user)).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response signup(@BeanParam @Valid SignupRequest request) {
        if (!applicationConfig.getSignUpEnabled()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        final var user = createUser.handle(request.toCreateUserInput(applicationConfig.getAutoConfirm()));
        final var emailAddress = user.getPrimaryEmailAddress();

        if (!emailAddress.getEmailVerified() && emailAddress.getEmailVerificationToken() != null) {
            sendMagicLinkVerifyEmail.handle(new EmailAddressInput(emailAddress));
        }

        if (request.getRedirectTo() != null) {
            return Response.temporaryRedirect(request.getRedirectTo()).build();
        }

        return Response.ok(new PrivateUserResponse(user)).build();
    }

    @POST
    @Transactional
    @Path("/verify-email")
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response verifyEmail(@QueryParam("token") String token, @QueryParam("redirect_to") URI redirectTo) {
        verifyMagicLink.handle(new VerifyMagicLinkInput(token));
        if (redirectTo != null) {
            return Response.temporaryRedirect(redirectTo).build();
        }

        return Response.noContent().build();
    }

    @POST
    @Transactional
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(@QueryParam("grant_type") String grantType,
            @QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("refresh_token") String refreshToken) {
        return switch (grantType) {
            case "password" -> {
                final var token = loginUserWithPassword.handle(new LoginPasswordInput(username, password));
                yield Response.ok(new TokenResponse(token)).build();
            }
            case "refresh_token" -> {
                final var token = refreshAccessToken.handle(new RefreshTokenInput(refreshToken));
                yield Response.ok(new TokenResponse(token)).build();
            }
            default -> Response.status(Response.Status.BAD_REQUEST).build();
        };
    }
}
