package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.VerifyEmail;
import dev.cloudeko.zenei.domain.model.email.ConfirmEmailInput;
import dev.cloudeko.zenei.domain.model.email.EmailAddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class VerifyEmailImpl implements VerifyEmail {

    private final EmailAddressRepository emailAddressRepository;

    @Override
    public void handle(ConfirmEmailInput input) {
        emailAddressRepository.confirmEmailAddress(input.getToken());
    }
}
