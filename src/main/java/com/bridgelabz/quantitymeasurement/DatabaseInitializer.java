package com.bridgelabz.quantitymeasurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the database schema automatically on application startup.
 */
public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initializeSchema() {
        loadDriver();
        String schema = readSchemaSql();
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getJdbcUrl(),
                DatabaseConfig.getJdbcUsername(),
                DatabaseConfig.getJdbcPassword());
             Statement statement = connection.createStatement()) {
            statement.execute(schema);
        } catch (SQLException e) {
            throw new QuantityMeasurementException("Failed to initialize database schema", e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(DatabaseConfig.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            throw new QuantityMeasurementException("JDBC driver not found", e);
        }
    }

    private static String readSchemaSql() {
        InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("schema.sql");
        if (inputStream == null) {
            throw new QuantityMeasurementException("schema.sql not found in resources");
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            throw new QuantityMeasurementException("Failed to read schema.sql", e);
        }
        return builder.toString();
    }
}
