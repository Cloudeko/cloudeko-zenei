package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.mail.CreateConfirmationEmailInput;

public interface SendCreateConfirmationEmail {
    boolean handle(CreateConfirmationEmailInput input);
}
