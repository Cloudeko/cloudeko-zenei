package dev.cloudeko.zenei.user.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class ExternalAuthBuildSteps {

    private static final String FEATURE = "zenei-user-account";

    @BuildStep
    public FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    /*@BuildStep
    public RouteBuildItem route(NonApplicationRootPathBuildItem nonApplicationRootPathBuildItem) {
        return nonApplicationRootPathBuildItem.routeBuilder()
                .route("user")
                .handler(new UserInfoHandler())
                .displayOnNotFoundPage()
                .build();
    }*/
}
