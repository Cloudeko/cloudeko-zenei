package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.SendConfirmationEmail;
import dev.cloudeko.zenei.domain.model.mail.ConfirmationTokenRepository;
import dev.cloudeko.zenei.domain.model.mail.EmailInput;
import dev.cloudeko.zenei.domain.provider.MailTemplateProvider;
import dev.cloudeko.zenei.domain.provider.StringTokenProvider;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import java.time.LocalDateTime;
import java.util.UUID;

@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class SendConfirmationEmailImpl implements SendConfirmationEmail {

    private final Mailer mailer;
    private final StringTokenProvider stringTokenProvider;
    private final MailTemplateProvider mailTemplateProvider;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public boolean handle(EmailInput input) {
        final var tokenString = stringTokenProvider.generateToken(input.getEmail() + UUID.randomUUID().toString());
        final var token = confirmationTokenRepository.createConfirmationToken(input.getEmail(), tokenString,
                LocalDateTime.now().plusMinutes(5));

        if (token == null) {
            return false;
        }

        try {
            final var content = mailTemplateProvider.defaultCreateConfirmationMailTemplate("http://localhost:8080/user/verify-email",
                    token.getToken());
            final var mail = Mail.withHtml(input.getEmail(), "Welcome to Zenei", content);

            mailer.send(mail);

            return true;
        } catch (Exception e) {
            log.error("Error sending email", e);
            return false;
        }
    }
}
