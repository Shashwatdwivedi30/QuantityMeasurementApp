package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "quantity_measurements",
        indexes = {
                @Index(name = "idx_operation",        columnList = "operation"),
                @Index(name = "idx_measurement_type", columnList = "thisMeasurementType"),
                @Index(name = "idx_created_at",       columnList = "createdAt"),
                @Index(name = "idx_is_error",         columnList = "isError")
        }
)
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private double resultValue;
    private String resultUnit;

    private String resultMeasurementType; // 🔥 Lombok हटाया
    private String resultString;

    private boolean isError;
    private String errorMessage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ---------------- Lifecycle ----------------

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ---------------- Constructors ----------------

    public QuantityMeasurementEntity() {}

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, String resultString) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.resultString = resultString;
    }

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, double resultValue, String resultUnit,
                                     String resultMeasurementType) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
    }

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, String errorMessage, boolean isError) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.errorMessage = errorMessage;
        this.isError = isError;
    }

    // ---------------- Getters & Setters ----------------

    public Long getId() { return id; }

    public double getThisValue() { return thisValue; }
    public void setThisValue(double v) { this.thisValue = v; }

    public String getThisUnit() { return thisUnit; }
    public void setThisUnit(String v) { this.thisUnit = v; }

    public String getThisMeasurementType() { return thisMeasurementType; }
    public void setThisMeasurementType(String v) { this.thisMeasurementType = v; }

    public double getThatValue() { return thatValue; }
    public void setThatValue(double v) { this.thatValue = v; }

    public String getThatUnit() { return thatUnit; }
    public void setThatUnit(String v) { this.thatUnit = v; }

    public String getThatMeasurementType() { return thatMeasurementType; }
    public void setThatMeasurementType(String v) { this.thatMeasurementType = v; }

    public String getOperation() { return operation; }
    public void setOperation(String v) { this.operation = v; }

    public double getResultValue() { return resultValue; }
    public void setResultValue(double v) { this.resultValue = v; }

    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String v) { this.resultUnit = v; }

    public String getResultMeasurementType() { return resultMeasurementType; }

    // 🔥 FIXED SETTER (MAIN ERROR SOLUTION)
    public void setResultMeasurementType(String resultMeasurementType) {
        this.resultMeasurementType = resultMeasurementType;
    }

    public String getResultString() { return resultString; }
    public void setResultString(String v) { this.resultString = v; }

    public boolean isError() { return isError; }
    public void setError(boolean v) { this.isError = v; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ---------------- equals & hashCode ----------------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityMeasurementEntity other)) return false;
        return Double.compare(thisValue, other.thisValue) == 0
                && Double.compare(thatValue, other.thatValue) == 0
                && Double.compare(resultValue, other.resultValue) == 0
                && isError == other.isError
                && Objects.equals(thisUnit, other.thisUnit)
                && Objects.equals(thatUnit, other.thatUnit)
                && Objects.equals(thisMeasurementType, other.thisMeasurementType)
                && Objects.equals(thatMeasurementType, other.thatMeasurementType)
                && Objects.equals(operation, other.operation)
                && Objects.equals(resultUnit, other.resultUnit)
                && Objects.equals(resultMeasurementType, other.resultMeasurementType)
                && Objects.equals(resultString, other.resultString)
                && Objects.equals(errorMessage, other.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisValue, thisUnit, thisMeasurementType,
                thatValue, thatUnit, thatMeasurementType,
                operation, resultValue, resultUnit,
                resultMeasurementType, resultString,
                isError, errorMessage);
    }

    // ---------------- toString ----------------

    @Override
    public String toString() {
        if (isError)
            return String.format("[%s] %.1f %s + %.1f %s | ERROR: %s",
                    operation, thisValue, thisUnit, thatValue, thatUnit, errorMessage);

        if (resultString != null)
            return String.format("[%s] %.1f %s == %.1f %s | Result: %s",
                    operation, thisValue, thisUnit, thatValue, thatUnit, resultString);

        return String.format("[%s] %.1f %s + %.1f %s | Result: %.2f %s",
                operation, thisValue, thisUnit, thatValue, thatUnit, resultValue, resultUnit);
    }
}