package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidConfirmationTokenException;
import dev.cloudeko.zenei.domain.feature.VerifyEmail;
import dev.cloudeko.zenei.domain.model.mail.ConfirmEmailInput;
import dev.cloudeko.zenei.domain.model.mail.ConfirmationTokenRepository;
import dev.cloudeko.zenei.domain.model.mail.EmailInput;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
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
public class VerifyEmailImpl implements VerifyEmail {

    private final Mailer mailer;
    private final StringTokenProvider stringTokenProvider;
    private final MailTemplateProvider mailTemplateProvider;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public boolean handle(ConfirmEmailInput input) {
        final var confirmationToken = confirmationTokenRepository.findByToken(input.getToken());

        if (confirmationToken.isEmpty()) {
            throw new InvalidConfirmationTokenException();
        }

        if (confirmationToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidConfirmationTokenException();
        }

        final var userId = confirmationToken.get().getUser();
        final var user = userRepository.getUserById(userId).orElseThrow();

        userRepository.updateEmailVerified(user.getEmail(), true);
        confirmationTokenRepository.deleteConfirmationToken(input.getToken());

        return true;
    }
}
