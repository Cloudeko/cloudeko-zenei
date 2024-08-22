package dev.cloudeko.zenei.user.runtime;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Pool;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

public class BasicUserAccountInitializer {

    private static final Logger log = Logger.getLogger(BasicUserAccountInitializer.class);
    private static final String FAILED_TO_CREATE_DB_TABLE = "unknown reason, please report the issue and create table manually";

    void initialize(@Observes StartupEvent event, Vertx vertx, Pool pool, UserAccountInitializerProperties initializerProps) {
        createDatabaseTable(pool, initializerProps.createTableDdl, initializerProps.supportsIfTableNotExists);
    }

    private static void createDatabaseTable(Pool pool, String createTableDdl, boolean supportsIfTableNotExists) {
        log.debugf("Creating database table with query: %s", createTableDdl);

        Uni<String> tableCreationResult = Uni.createFrom()
                .completionStage(pool.query(createTableDdl).execute().toCompletionStage())
                .onItemOrFailure()
                .transformToUni((rows, throwable) -> {
                    if (throwable != null) {
                        return supportsIfTableNotExists
                                ? Uni.createFrom().item(throwable.getMessage())
                                : Uni.createFrom().nullItem();
                    }

                    return verifyTableExists(pool);
                });

        String errMsg = tableCreationResult.await().indefinitely();
        if (errMsg != null) {
            throw new RuntimeException("OIDC Token State Manager failed to create database table: " + errMsg);
        }
    }

    private static Uni<String> verifyTableExists(Pool pool) {
        return Uni.createFrom()
                .completionStage(pool.query("SELECT MAX(id) FROM zenei_user_account").execute().toCompletionStage())
                .map(rows -> {
                    if (rows != null && rows.columnsNames().size() == 1) {
                        return null; // Table exists
                    }
                    return FAILED_TO_CREATE_DB_TABLE;
                })
                .onFailure().recoverWithItem(throwable -> {
                    log.error("Create database query failed with: ", throwable);
                    return FAILED_TO_CREATE_DB_TABLE;
                });
    }

    public record UserAccountInitializerProperties(String createTableDdl, boolean supportsIfTableNotExists) {
    }
}