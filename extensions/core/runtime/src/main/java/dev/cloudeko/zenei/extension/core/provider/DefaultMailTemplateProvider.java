package dev.cloudeko.zenei.extension.core.provider;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultMailTemplateProvider implements MailTemplateProvider {

    @Override
    public String format(String template, Object... args) {
        return String.format(template, args);
    }
}
