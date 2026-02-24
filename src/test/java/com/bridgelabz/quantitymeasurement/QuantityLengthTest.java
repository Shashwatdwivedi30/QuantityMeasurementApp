package com.bridgelabz.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityLengthTest {

    private static final double EPS = 1e-6;

    @Test
    public void testFeetToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(12.0, result, EPS);
    }

    @Test
    public void testInchesToFeet() {
        double result = QuantityLength.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET);
        assertEquals(2.0, result, EPS);
    }

    @Test
    public void testYardsToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES);
        assertEquals(36.0, result, EPS);
    }

    @Test
    public void testSameUnitConversion() {
        double result = QuantityLength.convert(5.0, LengthUnit.FEET, LengthUnit.FEET);
        assertEquals(5.0, result, EPS);
    }

    @Test
    public void testZeroValue() {
        double result = QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(0.0, result, EPS);
    }

    @Test
    public void testNegativeConversion() {
        double result = QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(-12.0, result, EPS);
    }

    @Test
    public void testCentimetersToInches() {
        double result = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        assertEquals(1.0, result, EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUnitThrows() {
        QuantityLength.convert(1.0, null, LengthUnit.INCHES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaNThrows() {
        QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInfinityThrows() {
        QuantityLength.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES);
    }

    @Test
    public void testEqualityWorks() {
        QuantityLength a = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength b = new QuantityLength(1.0, LengthUnit.FEET);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void testConvertTo() {
        QuantityLength length = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength converted = length.convertTo(LengthUnit.INCHES);

        assertEquals(36.0, converted.getValue(), EPS);
        assertEquals(LengthUnit.INCHES, converted.getUnit());
    }
}