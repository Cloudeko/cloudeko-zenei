package dev.cloudeko.zenei.extension.rest.endpoint.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cloudeko.zenei.extension.core.feature.FindUserByIdentifier;
import dev.cloudeko.zenei.extension.core.feature.UserDataManager;
import dev.cloudeko.zenei.extension.core.model.user.User;
import io.quarkus.arc.Arc;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.QuarkusHttpUser;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Handler that serves the user information of the authenticated user.
 */
public class UserInfoHandler extends OperationHandler {

    private static final String ALLOWED_METHODS = "GET, HEAD, OPTIONS";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private volatile UserDataManager userDataManager;

    public UserInfoHandler() {
        super(HttpMethod.GET);
    }

    @Override
    protected Response handleRequest(RoutingContext event) {
        QuarkusHttpUser quarkusUser = (QuarkusHttpUser) event.user();
        SecurityIdentity securityIdentity = quarkusUser.getSecurityIdentity();

        User user = getUserDataManager().findUserByIdentifier(securityIdentity.getPrincipal().getName());
        return Response.ok(user).build();
    }

    public UserDataManager getUserDataManager() {
        if (this.userDataManager == null) {
            this.userDataManager = Arc.container().instance(UserDataManager.class).get();
        }

        return this.userDataManager;
    }
}
