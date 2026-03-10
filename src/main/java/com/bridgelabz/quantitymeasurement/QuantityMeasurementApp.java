package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {

    public static double demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = Quantity.convert(value, from, to);
        System.out.println("Converted: " + result);
        return result;
    }

    public static Quantity demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity result = q.convertTo(to);
        System.out.println("Converted: " + result);
        return result;
    }

    public static void demonstrateLengthEquality(Quantity a, Quantity b) {
        System.out.println(a + " == " + b + " ? " + a.equals(b));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {
        Quantity q1 = new Quantity(v1, u1);
        Quantity q2 = new Quantity(v2, u2);

        demonstrateLengthEquality(q1, q2);
    }

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        Quantity yardObj = new Quantity(1.0, LengthUnit.YARDS);
        demonstrateLengthConversion(yardObj, LengthUnit.INCHES);

        demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);
    }
}