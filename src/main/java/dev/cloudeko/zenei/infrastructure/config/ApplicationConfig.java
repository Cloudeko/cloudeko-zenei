package dev.cloudeko.zenei.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Getter
@ApplicationScoped
public class ApplicationConfig {

    @ConfigProperty(name = "zenei.mailer.auto-confirm", defaultValue = "false")
    Boolean autoConfirm;
}
