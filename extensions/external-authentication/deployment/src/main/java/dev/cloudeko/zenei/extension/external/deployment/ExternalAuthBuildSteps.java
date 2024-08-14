package dev.cloudeko.zenei.extension.external.deployment;

import dev.cloudeko.zenei.extension.external.ExternalAuthProducer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.jboss.logging.Logger;

public class ExternalAuthBuildSteps {

    private static final String FEATURE = "external-authentication";

    @BuildStep
    public FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    public AdditionalBeanBuildItem producer() {
        return new AdditionalBeanBuildItem(ExternalAuthProducer.class);
    }
}
