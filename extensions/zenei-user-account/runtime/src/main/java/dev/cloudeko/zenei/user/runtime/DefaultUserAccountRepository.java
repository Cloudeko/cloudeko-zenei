package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.DatabaseUtil;
import dev.cloudeko.zenei.user.QueryRegistry;
import io.smallrye.mutiny.Uni;
import io.vertx.sqlclient.*;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DefaultUserAccountRepository {

    private static final Logger log = Logger.getLogger(DefaultUserAccountRepository.class);
    private static final String FAILED_TO_FIND_USER_BY_IDENTIFIER = "Failed to find user by identifier";

    private final Map<String, String> queries;
    private final Pool pool;

    public DefaultUserAccountRepository(QueryProvider queryProvider, Pool pool) {
        this.queries = queryProvider.queries();
        this.pool = pool;
    }

    public Uni<DefaultUserAccount> findUserByIdentifier(Long identifier) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_IDENTIFIER))
                        .execute(Tuple.of(identifier))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<DefaultUserAccount> findUserByUsername(String username) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_USERNAME))
                        .execute(Tuple.of(username))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<DefaultUserAccount> findUserByPrimaryEmailAddress(String email) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_PRIMARY_EMAIL_ADDRESS))
                        .execute(Tuple.of(email))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<DefaultUserAccount> findUserByPrimaryPhoneNumber(String phoneNumber) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_PRIMARY_PHONE_NUMBER))
                        .execute(Tuple.of(phoneNumber))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<List<DefaultUserAccount>> listUsers() {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_LIST))
                        .execute()
                        .toCompletionStage())
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<List<DefaultUserAccount>> listUsers(int page, int pageSize) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_LIST_PAGINATED))
                        .execute(Tuple.of(page, pageSize))
                        .toCompletionStage())
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    public Uni<DefaultUserAccount> createUser(DefaultUserAccount basicUserAccount) {
        Objects.requireNonNull(basicUserAccount, "User must not be null");
        Objects.requireNonNull(basicUserAccount.getUsername(), "Username must not be null");

        if (basicUserAccount.getId() != null) {
            throw new IllegalArgumentException("User ID must be null when creating a new user");
        }

        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_CREATE))
                                .execute(Tuple.of(basicUserAccount.getUsername())))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().transform(throwable -> new RuntimeException("Failed to create user", throwable));
    }

    public Uni<DefaultUserAccount> updateUser(DefaultUserAccount basicUserAccount) {
        Objects.requireNonNull(basicUserAccount, "User must not be null");
        Objects.requireNonNull(basicUserAccount.getId(), "User ID must not be null");
        Objects.requireNonNull(basicUserAccount.getUsername(), "Username must not be null");

        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_UPDATE))
                                .execute(Tuple.of(basicUserAccount.getUsername(),
                                        basicUserAccount.getId())))
                        .toCompletionStage())
                .onItem().transformToUni(DatabaseUtil::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::of)
                .onFailure().transform(throwable -> new RuntimeException("Failed to update user", throwable));
    }

    public Uni<Boolean> deleteUser(Long identifier) {
        Objects.requireNonNull(identifier, "User ID must not be null");

        return Uni.createFrom()
                .completionStage(pool
                        .withTransaction(client -> client
                                .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_DELETE))
                                .execute(Tuple.of(identifier)))
                        .toCompletionStage())
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows.rowCount() == 1))
                .onFailure().transform(throwable -> new RuntimeException("Failed to delete user", throwable));
    }

    public record QueryProvider(Map<String, String> queries) {
    }
}
