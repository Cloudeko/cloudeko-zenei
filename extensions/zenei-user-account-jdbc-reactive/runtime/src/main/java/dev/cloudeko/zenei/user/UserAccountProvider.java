package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserAccountProvider<ID> {
    Optional<UserAccount<ID>> findUserByIdentifier(ID identifier);
}
