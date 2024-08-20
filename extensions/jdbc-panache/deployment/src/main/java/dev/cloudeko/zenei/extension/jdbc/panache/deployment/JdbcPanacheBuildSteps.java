package dev.cloudeko.zenei.extension.jdbc.panache.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class JdbcPanacheBuildSteps {

    private static final String FEATURE = "jdbc-panache";

    @BuildStep
    public FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
