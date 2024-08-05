package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.email.EmailAddressInput;

public interface CreateEmailAddress {
    void handle(EmailAddressInput input);
}
