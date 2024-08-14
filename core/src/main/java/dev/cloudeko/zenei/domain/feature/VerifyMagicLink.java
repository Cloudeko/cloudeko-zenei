package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.email.VerifyMagicLinkInput;

public interface VerifyMagicLink {
    void handle(VerifyMagicLinkInput input);
}
