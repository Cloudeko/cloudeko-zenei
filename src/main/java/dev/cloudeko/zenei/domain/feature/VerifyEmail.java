package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.mail.ConfirmEmailInput;

public interface VerifyEmail {
    void handle(ConfirmEmailInput input);
}
