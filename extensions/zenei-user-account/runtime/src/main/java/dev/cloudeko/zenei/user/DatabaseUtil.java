package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.RowSet;

public class DatabaseUtil {
    public static Uni<Row> processNullableRow(RowSet<Row> rows) {
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
