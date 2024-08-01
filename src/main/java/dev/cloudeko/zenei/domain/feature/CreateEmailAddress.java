package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.email.EmailInput;

public interface CreateEmailAddress {
    void handle(EmailInput input);
}
