package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.InvalidConfirmationTokenException;
import dev.cloudeko.zenei.domain.feature.VerifyEmail;
import dev.cloudeko.zenei.domain.model.email.ConfirmEmailInput;
import dev.cloudeko.zenei.domain.model.email.ConfirmationTokenRepository;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import dev.cloudeko.zenei.domain.provider.MailTemplateProvider;
import dev.cloudeko.zenei.domain.provider.StringTokenProvider;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import java.time.LocalDateTime;

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
    public void handle(ConfirmEmailInput input) {
        final var confirmationToken = confirmationTokenRepository.findByToken(input.getToken());

        if (confirmationToken.isEmpty()) {
            throw new InvalidConfirmationTokenException();
        }

        if (confirmationToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidConfirmationTokenException();
        }

        final var user = confirmationToken.get().getUser();

        userRepository.updateEmailVerified(user.getEmail(), true);
        confirmationTokenRepository.deleteConfirmationToken(input.getToken());
    }
}
