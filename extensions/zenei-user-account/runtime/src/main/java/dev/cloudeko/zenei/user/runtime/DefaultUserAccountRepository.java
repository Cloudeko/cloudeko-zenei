package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.QueryRegistry;
import dev.cloudeko.zenei.user.UserAccountRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.sqlclient.*;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DefaultUserAccountRepository implements UserAccountRepositoryBase<DefaultUserAccount, Long> {

    private static final Logger log = Logger.getLogger(DefaultUserAccountRepository.class);
    private static final String FAILED_TO_FIND_USER_BY_IDENTIFIER = "Failed to find user by identifier";

    private final Map<String, String> queries;
    private final Pool pool;

    public DefaultUserAccountRepository(QueryProvider queryProvider, Pool pool) {
        this.queries = queryProvider.queries();
        this.pool = pool;
    }

    @Override
    public Uni<DefaultUserAccount> findUserByIdentifier(Long identifier) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_IDENTIFIER))
                        .execute(Tuple.of(identifier))
                        .toCompletionStage())
                .onItem().transformToUni(this::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::new)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    @Override
    public Uni<DefaultUserAccount> findUserByUsername(String username) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_FIND_BY_USERNAME))
                        .execute(Tuple.of(username))
                        .toCompletionStage())
                .onItem().transformToUni(this::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::new)
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    @Override
    public Uni<List<DefaultUserAccount>> listUsers() {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_LIST))
                        .execute()
                        .toCompletionStage())
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows)
                        .onItem().transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet))
                        .onItem().transform(DefaultUserAccount::new)
                        .collect().asList())
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    @Override
    public Uni<List<DefaultUserAccount>> listUsers(int page, int pageSize) {
        return Uni.createFrom()
                .completionStage(pool
                        .preparedQuery(queries.get(QueryRegistry.USER_ACCOUNT_LIST_PAGINATED))
                        .execute(Tuple.of(page, pageSize))
                        .toCompletionStage())
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows)
                        .onItem().transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet))
                        .onItem().transform(DefaultUserAccount::new)
                        .collect().asList())
                .onFailure().invoke(throwable -> log.error(FAILED_TO_FIND_USER_BY_IDENTIFIER, throwable));
    }

    @Override
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
                .onItem().transformToUni(this::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::new)
                .onFailure().transform(throwable -> new RuntimeException("Failed to create user", throwable));
    }

    @Override
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
                .onItem().transformToUni(this::processNullableRow)
                .onItem().ifNotNull().transform(DefaultUserAccount::new)
                .onFailure().transform(throwable -> new RuntimeException("Failed to update user", throwable));
    }

    @Override
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

    private Uni<Row> processNullableRow(RowSet<Row> rows) {
        if (rows.size() == 0) {
            return Uni.createFrom().nullItem();
        }

        final RowIterator<Row> iterator = rows.iterator();
        if (iterator.hasNext()) {
            return Uni.createFrom().item(iterator.next());
        }

        return Uni.createFrom().nullItem();
    }
}
