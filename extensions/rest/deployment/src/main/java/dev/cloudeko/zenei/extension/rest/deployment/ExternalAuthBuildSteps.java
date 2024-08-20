package dev.cloudeko.zenei.extension.rest.deployment;

import dev.cloudeko.zenei.extension.rest.endpoint.client.UserInfoHandler;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.http.deployment.NonApplicationRootPathBuildItem;
import io.quarkus.vertx.http.deployment.RouteBuildItem;

public class ExternalAuthBuildSteps {

    private static final String FEATURE = "core";

    @BuildStep
    public FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    public RouteBuildItem route(NonApplicationRootPathBuildItem nonApplicationRootPathBuildItem) {
        return nonApplicationRootPathBuildItem.routeBuilder()
                .route("user")
                .handler(new UserInfoHandler())
                .displayOnNotFoundPage()
                .build();
    }
}
