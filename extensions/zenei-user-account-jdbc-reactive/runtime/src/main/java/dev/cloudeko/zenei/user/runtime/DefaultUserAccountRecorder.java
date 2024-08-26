package dev.cloudeko.zenei.user.runtime;

import io.quarkus.runtime.annotations.Recorder;

import java.util.Map;
import java.util.function.Supplier;

@Recorder
public class DefaultUserAccountRecorder {

    public Supplier<DefaultUserAccountRepository.QueryProvider> createBasicUserAccountRepositoryQueryProvider(
            Map<String, String> config) {
        return () -> new DefaultUserAccountRepository.QueryProvider(config);
    }

    public Supplier<DefaultUserAccountInitializer.InitializerProperties> createUserAccountInitializerProps(
            Map<String, Boolean> tableSchemas) {
        return () -> new DefaultUserAccountInitializer.InitializerProperties(tableSchemas);
    }
}
