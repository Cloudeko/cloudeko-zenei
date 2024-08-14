package dev.cloudeko.zenei.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Getter
@ApplicationScoped
public class ApplicationConfig {

    @ConfigProperty(name = "zenei.auth.sign-up.enabled", defaultValue = "true")
    Boolean signUpEnabled;

    @ConfigProperty(name = "zenei.mailer.auto-confirm", defaultValue = "false")
    Boolean autoConfirm;

    @Inject
    DefaultUsersConfig defaultUsersConfig;
}
