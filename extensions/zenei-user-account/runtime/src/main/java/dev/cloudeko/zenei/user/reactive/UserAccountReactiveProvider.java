package dev.cloudeko.zenei.user.reactive;

import dev.cloudeko.zenei.user.UserAccount;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

public interface UserAccountReactiveProvider<ID> {
    Uni<UserAccount<ID>> findUserByIdentifier(ID identifier);
}
