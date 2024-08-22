package dev.cloudeko.zenei.user.deployment;

import io.quarkus.deployment.Capability;

public record QueryMetadata(String query, int parameters, String queryKey) {

    public QueryMetadata {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }
        if (queryKey == null || queryKey.isBlank()) {
            throw new IllegalArgumentException("Query key cannot be null or empty");
        }
    }

    public static QueryMetadata of(String query, int parameters, String queryKey) {
        return new QueryMetadata(query, parameters, queryKey);
    }

    public String getQuery(String client) {
        final String[] placeholders = new String[parameters];
        for (int i = 0; i < parameters; i++) {
            placeholders[i] = switch (client) {
                case Capability.REACTIVE_PG_CLIENT -> "$" + (i + 1);
                case Capability.REACTIVE_MSSQL_CLIENT -> "@p" + (i + 1);
                case Capability.REACTIVE_DB2_CLIENT, Capability.REACTIVE_ORACLE_CLIENT, Capability.REACTIVE_MYSQL_CLIENT -> "?";
                default -> throw new IllegalArgumentException("Unknown Reactive Sql Client " + client);
            };
        }

        return String.format(query, (Object[]) placeholders);
    }
}
