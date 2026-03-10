package com.bridgelabz.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {

    // --- UC14: Temperature Measurement Tests ---

    @Test
    public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(0.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    public void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertTrue(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
                .equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertTrue(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertTrue(new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                .equals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertTrue(new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                .equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)));
    }

    @Test
    public void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(c.equals(f));
        assertTrue(f.equals(c));
    }

    @Test
    public void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> f = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(f.equals(f));
    }

    @Test
    public void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        assertEquals(122.0,
                new Quantity<>(50.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
        assertEquals(-4.0,
                new Quantity<>(-20.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        assertEquals(50.0,
                new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
        assertEquals(-20.0,
                new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> f1 = new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> c = f1.convertTo(TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f2 = c.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(f1.getValue(), f2.getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_SameUnit() {
        assertEquals(100.0,
                new Quantity<>(100.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_ZeroValue() {
        assertEquals(32.0,
                new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_NegativeValues() {
        assertEquals(-40.0,
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureConversion_LargeValues() {
        assertEquals(1832.0,
                new Quantity<>(1000.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT).getValue(), 1e-6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Add() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Subtract() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTemperatureUnsupportedOperation_Divide() {
        new Quantity<>(100.0, TemperatureUnit.CELSIUS).divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
    }

    @Test
    public void testTemperatureUnsupportedOperation_ErrorMessage() {
        try {
            new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            assertEquals("Temperature does not support add operations.", e.getMessage());
        }
    }

    @Test
    public void testTemperatureVsLengthIncompatibility() {
        assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, LengthUnit.FEET)));
    }

    @Test
    public void testTemperatureVsWeightIncompatibility() {
        assertFalse(new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(50.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testTemperatureVsVolumeIncompatibility() {
        assertFalse(new Quantity<>(25.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(25.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    public void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    public void testIMeasurableInterface_Evolution_BackwardCompatible() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(WeightUnit.GRAM.supportsArithmetic());
        assertTrue(VolumeUnit.GALLON.supportsArithmetic());
    }

    @Test
    public void testTemperatureUnit_NonLinearConversion() {
        assertEquals(37.77777777777778,
                new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS).getValue(), 1e-6);
    }

    @Test
    public void testTemperatureUnit_AllConstants() {
        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
        assertNotNull(TemperatureUnit.KELVIN);
    }

    @Test
    public void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS", TemperatureUnit.CELSIUS.getUnitName());
    }

    @Test
    public void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionFactor(), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemperatureNullUnitValidation() {
        new Quantity<>(100.0, null);
    }

    @Test
    public void testTemperatureNullOperandValidation_InComparison() {
        assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(null));
    }

    @Test
    public void testTemperatureDifferentValuesInequality() {
        assertFalse(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
    }
}