package com.bridgelabz.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {

    @Test
    public void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(5.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(10.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(3.0, VolumeUnit.LITRE));
        assertEquals(7.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH));
        assertEquals(9.5, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCH)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(60.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Feet() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.FEET);
        assertEquals(9.5, result.getValue(), 1e-6);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Inches() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);
        assertEquals(114.0, result.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Millilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(3000.0, result.getValue(), 1e-6);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(-5.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_ResultingInZero() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(120.0, LengthUnit.INCH));
        assertEquals(0.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithZeroOperand() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0, LengthUnit.INCH));
        assertEquals(5.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithNegativeValues() {
        Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
                .subtract(new Quantity<>(-2.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        assertNotEquals(a.subtract(b).getValue(), b.subtract(a).getValue(), 1e-6);
        assertEquals(5.0, a.subtract(b).getValue(), 1e-6);
        assertEquals(-5.0, b.subtract(a).getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithLargeValues() {
        Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM));
        assertEquals(5e5, result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_WithSmallValues() {
        Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
                .subtract(new Quantity<>(0.0005, LengthUnit.FEET));
        assertEquals(0.0005, result.getValue(), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtraction_NullOperand() {
        new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtraction_NullTargetUnit() {
        new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), null);
    }

    @Test
    public void testSubtraction_AllMeasurementCategories() {
        assertNotNull(new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET)));
        assertNotNull(new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.GRAM)));
        assertNotNull(new Quantity<>(10.0, VolumeUnit.LITRE).subtract(new Quantity<>(5.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testSubtraction_ChainedOperations() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(2.0, LengthUnit.FEET))
                .subtract(new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(7.0, result.getValue(), 1e-6);
    }

    // --- UC12: Division Tests ---

    @Test
    public void testDivision_SameUnit_FeetDividedByFeet() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, 1e-6);
    }

    @Test
    public void testDivision_SameUnit_LitreDividedByLitre() {
        double result = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE));
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testDivision_CrossUnit_FeetDividedByInches() {
        double result = new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_CrossUnit_KilogramDividedByGram() {
        double result = new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_RatioGreaterThanOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertTrue(result > 1.0);
    }

    @Test
    public void testDivision_RatioLessThanOne() {
        double result = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertTrue(result < 1.0);
        assertEquals(0.5, result, 1e-6);
    }

    @Test
    public void testDivision_RatioEqualToOne() {
        double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(1.0, result, 1e-6);
    }

    @Test
    public void testDivision_NonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        assertNotEquals(a.divide(b), b.divide(a), 1e-6);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivision_ByZero() {
        new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET));
    }

    @Test
    public void testDivision_WithLargeRatio() {
        double result = new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));
        assertEquals(1e6, result, 1e-6);
    }

    @Test
    public void testDivision_WithSmallRatio() {
        double result = new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1e6, WeightUnit.KILOGRAM));
        assertEquals(1e-6, result, 1e-12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivision_NullOperand() {
        new Quantity<>(10.0, LengthUnit.FEET).divide(null);
    }

    @Test
    public void testDivision_AllMeasurementCategories() {
        assertEquals(2.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET)), 1e-6);
        assertEquals(2.0, new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)),
                1e-6);
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE)), 1e-6);
    }

    @Test
    public void testSubtractionAndDivision_Integration() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> c = new Quantity<>(4.0, LengthUnit.FEET);
        double result = a.subtract(b).divide(c);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSubtractionAddition_Inverse() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = a.add(b).subtract(b);
        assertEquals(a.getValue(), result.getValue(), 1e-6);
    }

    @Test
    public void testSubtraction_Immutability() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
        a.subtract(b);
        assertEquals(10.0, a.getValue(), 1e-6);
    }
}