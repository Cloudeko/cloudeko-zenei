package dev.cloudeko.zenei.extension.rest.endpoint.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cloudeko.zenei.extension.core.feature.FindUserByIdentifier;
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

/**
 * Handler that serves the user information of the authenticated user.
 */
public class UserInfoHandler implements Handler<RoutingContext> {

    private static final String ALLOWED_METHODS = "GET, HEAD, OPTIONS";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private volatile FindUserByIdentifier findUserByIdentifier;

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest req = routingContext.request();
        HttpServerResponse resp = routingContext.response();

        QuarkusHttpUser quarkusUser = (QuarkusHttpUser) routingContext.user();

        if (req.method().equals(HttpMethod.OPTIONS)) {
            resp.headers().set("Allow", ALLOWED_METHODS);
            routingContext.next();
        } else {
            resp.headers().set("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");
            SecurityIdentity securityIdentity = quarkusUser.getSecurityIdentity();
            User user = findUserByIdentifier(securityIdentity.getPrincipal().getName());
            try {
                resp.end(objectMapper.writeValueAsString(user));
            } catch (Exception e) {
                resp.setStatusCode(500).end();
            }
        }
    }

    public User findUserByIdentifier(String identifier) {
        if (this.findUserByIdentifier == null) {
            this.findUserByIdentifier = Arc.container().instance(FindUserByIdentifier.class).get();
        }

        return this.findUserByIdentifier.handle(identifier);
    }
}
