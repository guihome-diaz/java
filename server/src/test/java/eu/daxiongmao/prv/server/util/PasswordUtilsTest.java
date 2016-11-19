package eu.daxiongmao.prv.server.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import eu.daxiongmao.prv.server.util.PasswordUtils.PasswordValidationError;

public class PasswordUtilsTest {

    @Test
    public void testCheckPasswordStrength() {
        Set<PasswordValidationError> passwordErrors = PasswordUtils.checkPasswordStrength("Th1s_is-@t3st");
        Assert.assertNotNull(passwordErrors);
        Assert.assertTrue(passwordErrors.isEmpty());

        passwordErrors = PasswordUtils.checkPasswordStrength("i 1~hrà°$_DE;");
        Assert.assertTrue(passwordErrors.isEmpty());

        passwordErrors = PasswordUtils.checkPasswordStrength("aaaa11111____III");
        Assert.assertTrue(passwordErrors.isEmpty());

        passwordErrors = PasswordUtils.checkPasswordStrength("\n\t2I2JMq,ni edé=&# 4");
        Assert.assertTrue(passwordErrors.isEmpty());
    }

    @Test
    public void testCheckPasswordStrengthLengthFailures() {
        // Less than 10 chars
        Set<PasswordValidationError> passwordErrors = PasswordUtils.checkPasswordStrength("T1s_");
        Assert.assertNotNull(passwordErrors);
        Assert.assertEquals(1, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordUtils.PasswordValidationError.PASSWORD_TOO_SHORT));

        passwordErrors = PasswordUtils.checkPasswordStrength("T1s");
        Assert.assertEquals(2, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_SPECIAL_CHARACTER));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.PASSWORD_TOO_SHORT));

        // Empty or null
        passwordErrors = PasswordUtils.checkPasswordStrength(null);
        Assert.assertNotNull(passwordErrors);
        Assert.assertEquals(1, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.EMPTY_PASSWORD));

        passwordErrors = PasswordUtils.checkPasswordStrength("");
        Assert.assertEquals(1, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.EMPTY_PASSWORD));

        passwordErrors = PasswordUtils.checkPasswordStrength("   ");
        Assert.assertEquals(1, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.EMPTY_PASSWORD));

        // Long passwords
        passwordErrors = PasswordUtils.checkPasswordStrength(
                "aaaaaaaaaaaaaaaaaaa90=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assert.assertEquals(2, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.PASSWORD_TOO_LONG));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_UPPER_CASE_CHARACTER));

        passwordErrors = PasswordUtils.checkPasswordStrength(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assert.assertEquals(4, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.PASSWORD_TOO_LONG));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_SPECIAL_CHARACTER));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_UPPER_CASE_CHARACTER));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_DIGIT));

        passwordErrors = PasswordUtils.checkPasswordStrength(
                "GGGGGGGGGGGGGGGGGGGGGGGGGRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRHHHHHHHHHHHHHHHHHHHHHHHHHHHHH____AAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHH__111");
        Assert.assertEquals(2, passwordErrors.size());
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.PASSWORD_TOO_LONG));
        Assert.assertTrue(passwordErrors.contains(PasswordValidationError.NO_SMALL_CASE_CHARACTER));
    }

    @Test
    public void testGenerateSalt() throws NoSuchAlgorithmException {
        String salt1, salt2, salt3, salt4;
        salt1 = PasswordUtils.generateSalt();
        salt2 = PasswordUtils.generateSalt();
        salt3 = PasswordUtils.generateSalt();
        salt4 = PasswordUtils.generateSalt();

        Assert.assertFalse("Hashs must be different", salt1.equalsIgnoreCase(salt2) || salt1.equalsIgnoreCase(salt3) || salt1.equalsIgnoreCase(salt4));
        Assert.assertFalse("Hashs must be different", salt2.equalsIgnoreCase(salt3) || salt2.equalsIgnoreCase(salt4));
        Assert.assertFalse("Hashs must be different", salt3.equalsIgnoreCase(salt4));
    }

    @Test
    public void testGeneratePassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // conditions
        final int nbOfIterations = 200;
        final String salt = PasswordUtils.generateSalt();
        final String secret = "MyW%nd3rféä/__-&+°)#'{@ps12W240,?<";

        // Tests
        // Ensure algo can be written in small cases
        final String hash512 = PasswordUtils.getHash(nbOfIterations, secret, salt, "SHA-512");
        Assert.assertNotNull(hash512);
        final String hash512misspelled = PasswordUtils.getHash(nbOfIterations, secret, salt, "sha-512");
        Assert.assertNotNull(hash512misspelled);
        Assert.assertEquals(hash512, hash512misspelled);

        // Check another algorithm
        final String hash256 = PasswordUtils.getHash(nbOfIterations, secret, salt, "SHA-256");
        Assert.assertNotNull(hash256);
        Assert.assertFalse(hash512.equals(hash256));

        // Check default algorithm
        final String hashDefault = PasswordUtils.getHash(nbOfIterations, secret, salt, null);
        Assert.assertNotNull(hashDefault);
        Assert.assertTrue(hash512.equals(hashDefault));

        // Use default iterations
        final String hash512Long = PasswordUtils.getHash(null, secret, salt, "SHA-512");
        Assert.assertNotNull(hash512Long);
        Assert.assertEquals(hash512, hash512Long);

        final String hash256Long = PasswordUtils.getHash(null, secret, salt, "SHA-256");
        Assert.assertNotNull(hash256Long);
        Assert.assertEquals(hash256, hash256Long);
        Assert.assertFalse(hash512Long.equals(hash256Long));
    }

    @Test
    public void testGeneratePasswordUTF8() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // conditions
        final int nbOfIterations = 200;
        final String salt = PasswordUtils.generateSalt();
        final String secret = "MyW%nd3rféä/__-&+°)#'{@ps12W240,?< 大熊猫神法国人";
        // Test
        final String hash512 = PasswordUtils.getHash(nbOfIterations, secret, salt, "SHA-512");
        Assert.assertNotNull(hash512);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void testGeneratePasswordFailureAlgoDoesNotExist() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // conditions
        final int nbOfIterations = 200;
        final String salt = PasswordUtils.generateSalt();
        final String secret = "MyW%nd3rféä/__-&+°)#'{@ps12W240,?<";
        // Test
        PasswordUtils.getHash(nbOfIterations, secret, salt, "mySuperAlgo");
    }
}
