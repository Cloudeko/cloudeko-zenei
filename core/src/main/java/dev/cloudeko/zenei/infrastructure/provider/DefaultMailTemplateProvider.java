package dev.cloudeko.zenei.infrastructure.provider;

import dev.cloudeko.zenei.domain.provider.MailTemplateProvider;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultMailTemplateProvider implements MailTemplateProvider {

    @Override
    public String format(String template, Object... args) {
        return String.format(template, args);
    }
}
