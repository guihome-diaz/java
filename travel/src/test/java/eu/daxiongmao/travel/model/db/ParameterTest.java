package eu.daxiongmao.travel.model.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterTest {

    @Test
    public void getParamValueAsString() {
        final String expectation = "this is a nice String test";
        final Parameter param = new Parameter("test", expectation);
        final String paramValue = param.getValue(String.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getParamValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        Parameter param = new Parameter("test", expectation);
        Integer paramValue = param.getValue(Integer.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(19823, paramValue);

        // Empty case
        param = new Parameter("test", "   ");
        paramValue = param.getValue(Integer.class);
        Assertions.assertNull(paramValue);
    }

    @Test
    public void getParamValueAsBoolean() {
        // Nominal cases - explicit letters
        Parameter param = new Parameter("test", "true");
        Boolean paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("test", "  true  ");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("test", "false");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        param = new Parameter("test", "1");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("test", "0");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        param = new Parameter("test", "Y");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("test", "y");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("test", "N");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        param = new Parameter("test", "n");
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }
}
