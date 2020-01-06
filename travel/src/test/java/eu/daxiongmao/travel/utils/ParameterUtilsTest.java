package eu.daxiongmao.travel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterUtilsTest {

    @Test
    public void getValueAsString() {
        final String expectation = "this is a nice String test";
        final String paramValue = ParameterUtils.getValue(expectation, String.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getValueAsStringFromString() {
        final String expectation = "this is a nice String test";
        final String paramValue = (String) ParameterUtils.getValue(expectation, "java.lang.String");
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }


    @Test
    public void getValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        final Integer paramValue1 = ParameterUtils.getValue(expectation, Integer.class);
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final Integer paramValue2 = ParameterUtils.getValue("  ", Integer.class);
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getValueAsIntegerFromString() {
        // Nominal case
        final String expectation = "19823";
        final Integer paramValue1 = (Integer) ParameterUtils.getValue(expectation, "java.lang.Integer");
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final Integer paramValue2 = (Integer) ParameterUtils.getValue("  ", "java.lang.Integer");
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getValueAsBoolean() {
        // Nominal cases - explicit letters
        Boolean paramValue = ParameterUtils.getValue("true", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = ParameterUtils.getValue("false", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        paramValue = ParameterUtils.getValue("1", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = ParameterUtils.getValue("0", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        paramValue = ParameterUtils.getValue("Y", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = ParameterUtils.getValue("y", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = ParameterUtils.getValue("N", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        paramValue = ParameterUtils.getValue("n", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }

    @Test
    public void getValueAsBooleanFromString() {
        // Nominal cases - explicit letters
        Boolean paramValue = (Boolean) ParameterUtils.getValue("true", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) ParameterUtils.getValue("false", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        paramValue = (Boolean) ParameterUtils.getValue("1", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) ParameterUtils.getValue("0", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        paramValue = (Boolean) ParameterUtils.getValue("Y", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) ParameterUtils.getValue("y", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) ParameterUtils.getValue("N", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        paramValue = (Boolean) ParameterUtils.getValue("n", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }
}
