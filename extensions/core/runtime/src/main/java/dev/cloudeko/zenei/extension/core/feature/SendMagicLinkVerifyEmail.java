package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.email.EmailAddressInput;

public interface SendMagicLinkVerifyEmail {
    void handle(EmailAddressInput input);
}
