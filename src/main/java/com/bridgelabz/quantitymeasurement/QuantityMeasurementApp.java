package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {

    public static double demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println("Converted: " + result);
        return result;
    }

    public static QuantityLength demonstrateLengthConversion(QuantityLength q, LengthUnit to) {
        QuantityLength result = q.convertTo(to);
        System.out.println("Converted: " + result);
        return result;
    }

    public static void demonstrateLengthEquality(QuantityLength a, QuantityLength b) {
        System.out.println(a + " == " + b + " ? " + a.equals(b));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);

        demonstrateLengthEquality(q1, q2);
    }

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        QuantityLength yardObj = new QuantityLength(1.0, LengthUnit.YARDS);
        demonstrateLengthConversion(yardObj, LengthUnit.INCHES);

        demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);
    }
}