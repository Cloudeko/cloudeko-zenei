package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.email.EmailAddressInput;

public interface SendMagicLinkVerifyEmail {
    void handle(EmailAddressInput input);
}
