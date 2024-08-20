package dev.cloudeko.zenei.extension.rest.endpoint.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public abstract class OperationHandler implements Handler<RoutingContext> {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final HttpMethod method;

    public OperationHandler(HttpMethod method) {
        this.method = method;
    }

    protected abstract Response handleRequest(RoutingContext event);

    @Override
    public void handle(RoutingContext event) {
        if (event.request().method().equals(HttpMethod.OPTIONS)) {
            event.response().putHeader("Allow", method.toString() + ", OPTIONS");
            event.next();
            return;
        }

        if (!event.request().method().equals(method)) {
            event.response().setStatusCode(405).end();
            return;
        }

        try (Response response = handleRequest(event)) {
            event.response()
                    .putHeader("Content-Type", MediaType.APPLICATION_JSON)
                    .setStatusCode(response.getStatus())
                    .end(objectMapper.writeValueAsString(response.getEntity()));
        } catch (JsonProcessingException e) {
            event.response().setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).end();
        }
    }
}
