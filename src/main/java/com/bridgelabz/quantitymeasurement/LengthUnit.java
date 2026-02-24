package com.bridgelabz.quantitymeasurement;

public enum LengthUnit {
    FEET(1.0),          
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factorInFeet;

    LengthUnit(double factorInFeet) {
        this.factorInFeet = factorInFeet;
    }

    public double getConversionFactor() {
        return factorInFeet;
    }
}