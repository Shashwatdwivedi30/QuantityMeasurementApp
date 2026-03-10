package com.bridgelabz.quantitymeasurement;

public final class Quantity {

    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }
    public LengthUnit getUnit() {
        return unit;
    }

    public Quantity convertTo(LengthUnit targetUnit) {
        double converted = convert(value, unit, targetUnit);
        return new Quantity(converted, targetUnit);
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null.");
        }

        if (source == target) {
            return value;
        }

        double valueInFeet = value * source.getConversionFactor();

        return valueInFeet / target.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quantity)) return false;

        Quantity other = (Quantity) obj;

        double thisInFeet = convert(this.value, this.unit, LengthUnit.FEET);
        double otherInFeet = convert(other.value, other.unit, LengthUnit.FEET);

        return Math.abs(thisInFeet - otherInFeet) < 1e-6;
    }

    @Override
    public int hashCode() {
        double valueInFeet = convert(value, unit, LengthUnit.FEET);
        return Double.hashCode(valueInFeet);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}