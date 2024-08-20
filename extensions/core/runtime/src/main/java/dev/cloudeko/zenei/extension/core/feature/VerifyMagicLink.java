package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.email.VerifyMagicLinkInput;

public interface VerifyMagicLink {
    void handle(VerifyMagicLinkInput input);
}
