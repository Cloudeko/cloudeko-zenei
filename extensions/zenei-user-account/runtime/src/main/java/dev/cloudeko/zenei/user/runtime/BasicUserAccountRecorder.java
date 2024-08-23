package dev.cloudeko.zenei.user.runtime;

import io.quarkus.runtime.annotations.Recorder;

import java.util.Map;
import java.util.function.Supplier;

@Recorder
public class BasicUserAccountRecorder {

    public Supplier<BasicUserAccountRepository.QueryProvider> createBasicUserAccountRepositoryQueryProvider(Map<String, String> config) {
        return () -> new BasicUserAccountRepository.QueryProvider(config);
    }

    public Supplier<BasicUserAccountInitializer.UserAccountInitializerProperties> createUserAccountInitializerProps(
            String createTableDdl,
            boolean supportsIfTableNotExists) {
        return () -> new BasicUserAccountInitializer.UserAccountInitializerProperties(createTableDdl, supportsIfTableNotExists);
    }
}
