package dev.cloudeko.zenei.user.reactive;

import dev.cloudeko.zenei.user.UserQueryProvider;

public interface UserReactiveManager<ID> extends UserAccountRegistrationReactiveProvider<ID>, UserAccountReactiveProvider<ID>, UserQueryProvider<ID> {
}
