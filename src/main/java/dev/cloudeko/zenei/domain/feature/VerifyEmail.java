package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.mail.ConfirmEmailInput;
import dev.cloudeko.zenei.domain.model.mail.EmailInput;

public interface VerifyEmail {
    boolean handle(ConfirmEmailInput input);
}
