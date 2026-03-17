package com.bridgelabz.quantitymeasurement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC repository implementation for persistent storage of measurement history.
 */
public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final String INSERT_SQL =
            "INSERT INTO quantity_measurement_history (" +
                    "operation_type, left_value, left_unit, right_value, right_unit, " +
                    "result_value, result_unit, scalar_result, error, error_message" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT operation_type, left_value, left_unit, right_value, right_unit, " +
                    "result_value, result_unit, scalar_result, error, error_message, created_at " +
                    "FROM quantity_measurement_history ORDER BY id";

    public QuantityMeasurementDatabaseRepository() {
        DatabaseInitializer.initializeSchema();
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            return;
        }

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            connection.setAutoCommit(false);
            bindEntity(statement, entity);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new QuantityMeasurementException("Failed to save measurement history to database", e);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        List<QuantityMeasurementEntity> entities = new ArrayList<>();
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new QuantityMeasurementException("Failed to fetch measurement history from database", e);
        }
        return entities;
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.getJdbcUrl(),
                DatabaseConfig.getJdbcUsername(),
                DatabaseConfig.getJdbcPassword());
    }

    private void bindEntity(PreparedStatement statement, QuantityMeasurementEntity entity) throws SQLException {
        statement.setString(1, entity.getOperationType().name());
        setQuantity(statement, 2, entity.getLeftOperand());
        setQuantity(statement, 4, entity.getRightOperand());
        setQuantity(statement, 6, entity.getResultQuantity());
        setNullableDouble(statement, 8, entity.getScalarResult());
        statement.setBoolean(9, entity.hasError());
        statement.setString(10, entity.getErrorMessage());
    }

    private void setQuantity(PreparedStatement statement, int startIndex, QuantityModel<?> quantity) throws SQLException {
        if (quantity == null) {
            statement.setNull(startIndex, java.sql.Types.DOUBLE);
            statement.setNull(startIndex + 1, java.sql.Types.VARCHAR);
            return;
        }
        statement.setDouble(startIndex, quantity.getValue());
        statement.setString(startIndex + 1, quantity.getUnit().getUnitName());
    }

    private void setNullableDouble(PreparedStatement statement, int parameterIndex, Double value) throws SQLException {
        if (value == null) {
            statement.setNull(parameterIndex, java.sql.Types.DOUBLE);
            return;
        }
        statement.setDouble(parameterIndex, value);
    }

    private QuantityMeasurementEntity mapRow(ResultSet resultSet) throws SQLException {
        QuantityMeasurementEntity.OperationType operationType =
                QuantityMeasurementEntity.OperationType.valueOf(resultSet.getString("operation_type"));

        QuantityModel<IMeasurable> leftOperand = mapQuantity(
                resultSet.getObject("left_value") != null ? resultSet.getDouble("left_value") : null,
                resultSet.getString("left_unit"));
        QuantityModel<IMeasurable> rightOperand = mapQuantity(
                resultSet.getObject("right_value") != null ? resultSet.getDouble("right_value") : null,
                resultSet.getString("right_unit"));
        QuantityModel<IMeasurable> resultQuantity = mapQuantity(
                resultSet.getObject("result_value") != null ? resultSet.getDouble("result_value") : null,
                resultSet.getString("result_unit"));

        boolean error = resultSet.getBoolean("error");
        String errorMessage = resultSet.getString("error_message");
        Double scalarResult = resultSet.getObject("scalar_result") != null
                ? resultSet.getDouble("scalar_result")
                : null;
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        QuantityMeasurementEntity entity;
        if (error) {
            entity = new QuantityMeasurementEntity(leftOperand, rightOperand, operationType, errorMessage);
        } else if (resultQuantity != null && rightOperand == null) {
            entity = new QuantityMeasurementEntity(leftOperand, operationType, resultQuantity);
        } else if (resultQuantity != null) {
            entity = new QuantityMeasurementEntity(leftOperand, rightOperand, operationType, resultQuantity);
        } else {
            entity = new QuantityMeasurementEntity(leftOperand, rightOperand, operationType, scalarResult);
        }
        if (createdAt != null) {
            entity.setCreatedAt(createdAt.toString());
        }
        return entity;
    }

    private QuantityModel<IMeasurable> mapQuantity(Double value, String unitName) {
        if (value == null || unitName == null) {
            return null;
        }
        return new QuantityModel<IMeasurable>(value, resolveUnit(unitName));
    }

    private IMeasurable resolveUnit(String unitName) {
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.name().equals(unitName)) {
                return unit;
            }
        }
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.name().equals(unitName)) {
                return unit;
            }
        }
        for (VolumeUnit unit : VolumeUnit.values()) {
            if (unit.name().equals(unitName)) {
                return unit;
            }
        }
        for (TemperatureUnit unit : TemperatureUnit.values()) {
            if (unit.name().equals(unitName)) {
                return unit;
            }
        }
        throw new QuantityMeasurementException("Unknown unit in database: " + unitName);
    }
}
