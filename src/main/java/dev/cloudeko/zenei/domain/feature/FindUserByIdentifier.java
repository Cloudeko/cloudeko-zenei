package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.user.User;

public interface FindUserByIdentifier {
    User handle(Long identifier);
}
