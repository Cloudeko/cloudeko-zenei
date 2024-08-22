package dev.cloudeko.zenei.user.runtime;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;
import io.vertx.sqlclient.Pool;

import java.util.Map;
import java.util.function.Supplier;

@Recorder
public class BasicUserAccountRecorder {

    public void setSqlClientPool(BeanContainer container) {
        container.beanInstance(BasicUserAccountRepository.class).setSqlClientPool(container.beanInstance(Pool.class));
    }

    public Supplier<BasicUserAccountRepository> createBasicUserAccountRepository(Map<String, String> config) {
        return () -> new BasicUserAccountRepository(config);
    }

    public Supplier<BasicUserAccountInitializer.UserAccountInitializerProperties> createUserAccountInitializerProps(
            String createTableDdl,
            boolean supportsIfTableNotExists) {
        return () -> new BasicUserAccountInitializer.UserAccountInitializerProperties(createTableDdl, supportsIfTableNotExists);
    }
}
