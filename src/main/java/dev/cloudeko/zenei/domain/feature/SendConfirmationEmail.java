package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.mail.EmailInput;

public interface SendConfirmationEmail {
    boolean handle(EmailInput input);
}
