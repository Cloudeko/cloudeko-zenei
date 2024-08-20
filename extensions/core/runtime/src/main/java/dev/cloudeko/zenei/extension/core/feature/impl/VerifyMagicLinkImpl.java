package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.exception.InvalidConfirmationTokenException;
import dev.cloudeko.zenei.extension.core.feature.VerifyMagicLink;
import dev.cloudeko.zenei.extension.core.model.email.VerifyMagicLinkInput;
import dev.cloudeko.zenei.extension.core.repository.EmailAddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class VerifyMagicLinkImpl implements VerifyMagicLink {

    private final EmailAddressRepository emailAddressRepository;

    @Override
    public void handle(VerifyMagicLinkInput input) {
        if (!emailAddressRepository.confirmEmailAddress(input.getToken())) {
            throw new InvalidConfirmationTokenException();
        }
    }
}
