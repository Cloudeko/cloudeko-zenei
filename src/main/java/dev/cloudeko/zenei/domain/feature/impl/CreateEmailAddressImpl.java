package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.feature.CreateEmailAddress;
import dev.cloudeko.zenei.domain.model.email.ConfirmationTokenRepository;
import dev.cloudeko.zenei.domain.model.email.EmailAddressInput;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.MailTemplateProvider;
import dev.cloudeko.zenei.domain.provider.StringTokenProvider;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import java.util.UUID;

@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class CreateEmailAddressImpl implements CreateEmailAddress {

    private final Mailer mailer;
    private final StringTokenProvider stringTokenProvider;
    private final MailTemplateProvider mailTemplateProvider;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void handle(EmailAddressInput input) {
        final var user = userRepository.getUserByEmail(input.getEmailAddress());

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var token = stringTokenProvider.generateToken("mail", input.getEmailAddress() + UUID.randomUUID());


        final var content = mailTemplateProvider.defaultCreateConfirmationMailTemplate(
                "http://localhost:8080/user/verify-email",
                input.getEmailVerificationToken());
        final var mail = Mail.withHtml(input.getEmailAddress(), "Welcome to Zenei", content);

        mailer.send(mail);
    }
}
