package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.email.ConfirmEmailInput;

public interface VerifyEmail {
    void handle(ConfirmEmailInput input);
}
