package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.SendMagicLinkVerifyEmail;
import dev.cloudeko.zenei.domain.provider.MailTemplateProvider;
import dev.cloudeko.zenei.domain.provider.StringTokenProvider;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddressInput;
import dev.cloudeko.zenei.extension.core.repository.EmailAddressRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class SendMagicLinkVerifyEmailImpl implements SendMagicLinkVerifyEmail {

    private final Mailer mailer;
    private final StringTokenProvider stringTokenProvider;
    private final MailTemplateProvider mailTemplateProvider;

    private final UserRepository userRepository;
    private final EmailAddressRepository emailAddressRepository;

    @Override
    public void handle(EmailAddressInput input) {
        final var content = mailTemplateProvider.defaultCreateConfirmationMailTemplate(
                "http://localhost:8080/frontend/verify-email",
                input.getEmailAddress().getEmailVerificationToken());
        final var mail = Mail.withHtml(input.getEmailAddress().getEmail(), "Welcome to Zenei", content);

        mailer.send(mail);
    }
}
