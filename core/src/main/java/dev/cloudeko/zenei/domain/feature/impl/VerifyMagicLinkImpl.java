package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.VerifyMagicLink;
import dev.cloudeko.zenei.domain.model.email.EmailAddressRepository;
import dev.cloudeko.zenei.domain.model.email.VerifyMagicLinkInput;
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
        emailAddressRepository.confirmEmailAddress(input.getToken());
    }
}
