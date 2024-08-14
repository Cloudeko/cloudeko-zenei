package dev.cloudeko.zenei.infrastructure.config;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.config.FallbackConfigSourceInterceptor;
import jakarta.annotation.Priority;

import java.util.Map;

@Priority(150)
@RegisterForReflection
public class ConfigFallbackInterceptor extends FallbackConfigSourceInterceptor {

    public static final Map<String, String> MAPPING = Map.ofEntries(
            Map.entry("quarkus.datasource.db-kind", "zenei.database.db-kind"),
            Map.entry("quarkus.datasource.username", "zenei.database.username"),
            Map.entry("quarkus.datasource.password", "zenei.database.password"),
            Map.entry("quarkus.datasource.jdbc.url", "zenei.database.url"),
            Map.entry("smallrye.jwt.sign.key.location", "zenei.jwt.private.key.location"),
            Map.entry("smallrye.jwt.new-token.lifespan", "zenei.jwt.token.lifespan"),
            Map.entry("mp.jwt.verify.publickey", "zenei.jwt.private.key"),
            Map.entry("mp.jwt.verify.publickey.location", "zenei.jwt.public.key.location"),
            Map.entry("mp.jwt.verify.publickey.algorithm", "zenei.jwt.public.key.algorithm"),
            Map.entry("mp.jwt.verify.issuer", "zenei.jwt.issuer"),
            Map.entry("mp.jwt.verify.audiences", "zenei.jwt.audiences"),
            Map.entry("mp.jwt.verify.clock.skew", "zenei.jwt.clock.skew"),
            Map.entry("mp.jwt.verify.token.age", "zenei.jwt.token.age")
    );

    public ConfigFallbackInterceptor() {
        super(name -> MAPPING.getOrDefault(name, name));
    }
}
