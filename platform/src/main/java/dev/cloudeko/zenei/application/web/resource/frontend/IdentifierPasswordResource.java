package dev.cloudeko.zenei.application.web.resource.frontend;

import dev.cloudeko.zenei.application.web.model.request.LoginRequest;
import dev.cloudeko.zenei.application.web.model.request.RegisterRequest;
import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.application.web.model.response.SessionTokenResponse;
import dev.cloudeko.zenei.extension.core.config.ApplicationConfig;
import dev.cloudeko.zenei.extension.core.feature.*;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddressInput;
import dev.cloudeko.zenei.extension.core.model.email.VerifyMagicLinkInput;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Path("/frontend")
@AllArgsConstructor
@Tag(name = "Email Password Service", description = "API for user authentication with email and password")
public class IdentifierPasswordResource {

    private final ApplicationConfig applicationConfig;

    private final CreateUser createUser;
    private final FindUserByIdentifier findUserByIdentifier;

    private final VerifyMagicLink verifyMagicLink;
    private final RefreshAccessToken refreshAccessToken;
    private final SendMagicLinkVerifyEmail sendMagicLinkVerifyEmail;

    private final LoginUserWithPassword loginUserWithPassword;
    private final LoginUserWithAuthorizationCode loginUserWithAuthorizationCode;

    @POST
    @Transactional
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@BeanParam @Valid RegisterRequest request) {
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
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@BeanParam @Valid LoginRequest request) {
        final var token = loginUserWithPassword.handle(request.getIdentifier(), request.getPassword());
        return Response.ok(new SessionTokenResponse(token)).build();
    }

    @POST
    @Transactional
    @Path("/verify-email")
    public Response verifyEmail(@QueryParam("token") String token, @QueryParam("redirect_to") URI redirectTo) {
        verifyMagicLink.handle(new VerifyMagicLinkInput(token));
        if (redirectTo != null) {
            return Response.temporaryRedirect(redirectTo).build();
        }

        return Response.noContent().build();
    }
}
