package eu.daxiongmao.travel.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterDTOTest {

    @Test
    public void getParamValueAsString() {
        final String expectation = "this is a nice String test";
        final ParameterDTO param = new ParameterDTO("test", expectation, "Test parameter", true);
        final String paramValue = param.getValue(String.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getParamValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        final ParameterDTO param1 = new ParameterDTO("test", expectation, "Test parameter", true);
        final Integer paramValue1 = param1.getValue(Integer.class);
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final ParameterDTO param2 = new ParameterDTO("test", "  ", "Test parameter", true);
        final Integer paramValue2 = param2.getValue(Integer.class);
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getParamValueAsBoolean() {
        // Nominal cases - explicit letters
        ParameterDTO param = new ParameterDTO("test", "true", "Test parameter", true);
        Boolean paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("test", "false", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        param = new ParameterDTO("test", "1", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("test", "0", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        param = new ParameterDTO("test", "Y", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("test", "y", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("test", "N", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        param = new ParameterDTO("test", "n", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }
}
