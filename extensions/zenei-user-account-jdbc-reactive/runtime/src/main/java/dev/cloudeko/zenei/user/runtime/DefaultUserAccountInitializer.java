package dev.cloudeko.zenei.user.runtime;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Pool;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.Map;

public class DefaultUserAccountInitializer {

    private static final Logger log = Logger.getLogger(DefaultUserAccountInitializer.class);
    private static final String FAILED_TO_CREATE_DB_TABLE = "unknown reason, please report the issue and create table manually";

    void initialize(@Observes StartupEvent event, Vertx vertx, Pool pool, InitializerProperties properties) {
        log.debug("Initializing user account database tables");

        Map<String, Boolean> tableSchemas = properties.tables();
        if (tableSchemas == null || tableSchemas.isEmpty()) {
            log.warn("No table schemas provided, skipping database table creation");
            return;
        }

        tableSchemas.forEach((ddl, supportsIfNotExists) -> {
            createDatabaseTable(pool, ddl, supportsIfNotExists);
        });
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
            throw new RuntimeException("Failed to create database table: " + errMsg);
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

    public record InitializerProperties(Map<String, Boolean> tables) {

    }
}