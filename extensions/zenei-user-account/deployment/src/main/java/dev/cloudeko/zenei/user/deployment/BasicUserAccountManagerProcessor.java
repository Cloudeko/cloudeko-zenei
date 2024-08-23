package dev.cloudeko.zenei.user.deployment;

import dev.cloudeko.zenei.user.runtime.DefaultUserAccountInitializer;
import dev.cloudeko.zenei.user.runtime.DefaultUserAccountManager;
import dev.cloudeko.zenei.user.runtime.DefaultUserAccountRecorder;
import dev.cloudeko.zenei.user.runtime.DefaultUserAccountRepository;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.builder.item.SimpleBuildItem;
import io.quarkus.deployment.Capabilities;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Singleton;
import org.jboss.jandex.DotName;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static io.quarkus.deployment.Capability.*;
import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

public class BasicUserAccountManagerProcessor {

    private static final String[] SUPPORTED_REACTIVE_CLIENTS = new String[] { REACTIVE_PG_CLIENT, REACTIVE_MYSQL_CLIENT,
            REACTIVE_MSSQL_CLIENT, REACTIVE_DB2_CLIENT, REACTIVE_ORACLE_CLIENT };

    @BuildStep
    ReactiveClientBuildItem findReactiveClient(Capabilities capabilities) {
        final String reactiveClient = capabilities.getCapabilities().stream()
                .filter(c -> Arrays.asList(SUPPORTED_REACTIVE_CLIENTS).contains(c))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No supported reactive SQL client found"));

        return new ReactiveClientBuildItem(reactiveClient);
    }

    @BuildStep
    @Record(STATIC_INIT)
    SyntheticBeanBuildItem createBasicUserInitializerConfig(DefaultUserAccountRecorder recorder,
            Capabilities capabilities) {
        final Map<String, Boolean> tableSchemas = Arrays.stream(DefaultTableSchema.schemas(capabilities))
                .collect(Collectors.toMap(TableSchema::getDdl, TableSchema::isSupportsIfNotExists));

        return SyntheticBeanBuildItem
                .configure(DefaultUserAccountInitializer.InitializerProperties.class)
                .supplier(recorder.createUserAccountInitializerProps(tableSchemas))
                .scope(Dependent.class)
                .unremovable()
                .done();
    }

    @BuildStep
    @Record(STATIC_INIT)
    SyntheticBeanBuildItem createBasicUserAccountRepository(DefaultUserAccountRecorder recorder,
            ReactiveClientBuildItem reactiveClient) {
        final Map<String, String> config = Arrays.stream(DefaultQuery.values())
                .collect(Collectors.toMap(defaultQuery -> defaultQuery.getMetadata().queryKey(),
                        defaultQuery -> defaultQuery.getMetadata().getQuery(reactiveClient.getReactiveClient())));

        return SyntheticBeanBuildItem
                .configure(DefaultUserAccountRepository.QueryProvider.class)
                .supplier(recorder.createBasicUserAccountRepositoryQueryProvider(config))
                .scope(Dependent.class)
                .unremovable()
                .done();
    }

    @BuildStep
    AdditionalBeanBuildItem setupBeans() {
        AdditionalBeanBuildItem.Builder builder = AdditionalBeanBuildItem.builder();
        builder.setDefaultScope(DotName.createSimple(Singleton.class.getName()));

        builder.addBeanClass(DefaultUserAccountInitializer.class);
        builder.addBeanClass(DefaultUserAccountRepository.class);

        return builder.build();
    }

    @BuildStep
    AdditionalBeanBuildItem setupManager() {
        return new AdditionalBeanBuildItem(DefaultUserAccountManager.class);
    }

    private static final class ReactiveClientBuildItem extends SimpleBuildItem {

        private final String reactiveClient;

        private ReactiveClientBuildItem(String reactiveClient) {
            this.reactiveClient = reactiveClient;
        }

        public String getReactiveClient() {
            return reactiveClient;
        }
    }
}
