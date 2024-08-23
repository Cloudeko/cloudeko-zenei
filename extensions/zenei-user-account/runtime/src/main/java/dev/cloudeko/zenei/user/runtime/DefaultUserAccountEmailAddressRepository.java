package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.DatabaseUtil;
import dev.cloudeko.zenei.user.EmailAddress;
import dev.cloudeko.zenei.user.QueryRegistry;
import io.smallrye.mutiny.Uni;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Tuple;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

public class DefaultUserAccountEmailAddressRepository {

    private static final Logger log = Logger.getLogger(DefaultUserAccountRepository.class);
    private static final String FAILED_TO_FIND_USER_BY_IDENTIFIER = "Failed to find user by identifier";

    private final Map<String, String> queries;
    private final Pool pool;

    public DefaultUserAccountEmailAddressRepository(DefaultUserAccountRepository.QueryProvider queryProvider, Pool pool) {
        this.queries = queryProvider.queries();
        this.pool = pool;
    }

    public Uni<List<DefaultEmailAddress>> listUserAccountEmailAddresses(Long identifier) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_LIST_BY_USER_ID))
                        .execute()
                        .toCompletionStage())
                .onItem().ifNotNull().transform(DefaultEmailAddress::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<DefaultEmailAddress> createUserAccountEmailAddress(Long identifier, DefaultEmailAddress emailAddress) {
        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_CREATE))
                                .execute(Tuple.of(identifier, emailAddress.getEmail(), emailAddress.isPrimaryEmail(),
                                        emailAddress.isEmailVerified())))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultEmailAddress::of)
                .onFailure().transform(throwable -> new RuntimeException("Failed to create user", throwable));
    }

    public Uni<DefaultEmailAddress> updateUserAccountEmailAddress(Long identifier, DefaultEmailAddress emailAddress) {
        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_UPDATE))
                                .execute(Tuple.of(identifier, emailAddress.getEmail(), emailAddress.isPrimaryEmail(),
                                        emailAddress.isEmailVerified())))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultEmailAddress::of)
                .onFailure().transform(throwable -> new RuntimeException("Failed to update user", throwable));
    }

    public Uni<Boolean> deleteUserAccountEmailAddress(Long identifier, String email) {
        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_DELETE))
                                .execute(Tuple.of(identifier, email))
                        )
                        .toCompletionStage())
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows.rowCount() == 1))
                .onFailure().transform(throwable -> new RuntimeException("Failed to delete user", throwable));
    }

    public Uni<Boolean> deleteUserAccountEmailAddresses(Long identifier) {
        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_EMAIL_ADDRESSES_DELETE_BY_USER_ID))
                                .execute(Tuple.of(identifier))
                        )
                        .toCompletionStage())
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows.rowCount() > 0))
                .onFailure().transform(throwable -> new RuntimeException("Failed to delete user", throwable));
    }
}
