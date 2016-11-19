package eu.daxiongmao.prv.server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Set of methods to create & check user passwords using OWASP recommendations.
 * See <a href="https://www.owasp.org/index.php/Hashing_Java">OWASP website</a>.
 * <br>
 * You can also consult <a href="https://crackstation.net/hashing-security.htm">CrackStation</a> to see their tutorials.<br>
 * <br>
 * You can run this class as a standalone tool to generate some passwords and salts.
 * @author Guillaume Diaz
 * @version 1.0 - May 2015
 * @version 1.1 - November 2016 (add main method)
 * @since release 0.1
 */
public final class PasswordUtils {

    private final static int NB_OF_ITERATIONS = 200;

    /**
     * Name of th hash algorithm to use. <br>
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest">Java
     * message digest</a> for the list of supported algorithms.
     */
    public final static String DEFAULT_HASH_ALGO = "SHA-512";

    /**
     * Name of the true & secure random algorithm to use.<br>
     * Java support different algorithms, <br>
     * see: <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom">Oracle javadoc</a>
     */
    public final static String DEFAULT_RANDOM_ALGORITHM = "SHA1PRNG";

    private PasswordUtils() {
    }

    public static void main(final String[] args) throws Exception {
        System.out.println("######################");
        System.out.println("# Password generator #");
        System.out.println("######################");
        final String salt = generateSalt();
        System.out.println("Please enter you password: ");
        final Scanner keyboardInput = new Scanner(System.in);
        final String secret = keyboardInput.next();
        keyboardInput.close();
        final String hash = getHash(NB_OF_ITERATIONS, secret, salt, DEFAULT_HASH_ALGO);
        System.out.println("Nb of iterations: " + NB_OF_ITERATIONS);
        System.out.println("Hash algorithm  : " + DEFAULT_HASH_ALGO);
        System.out.println("Salt: " + salt);
        System.out.println("Hash: " + hash);
    }

    public enum PasswordValidationError {
        EMPTY_PASSWORD, PASSWORD_TOO_SHORT, PASSWORD_TOO_LONG, NO_DIGIT, NO_SMALL_CASE_CHARACTER, NO_UPPER_CASE_CHARACTER, NO_SPECIAL_CHARACTER;
    }

    /**
     * Password complexity rule, according to OWASP recommendations:
     * <ul>
     * <li>Content check:
     * <ol>
     * <li>at least 1 uppercase character (A-Z)</li>
     * <li>at least 1 lowercase character (a-z)</li>
     * <li>at least 1 digit (0-9)</li>
     * <li>at least 1 special character (punctuation) — space is a special character too</li>
     * </ol>
     * </li>
     * <li>Size check:
     * <ol>
     * <li>at least 10 characters</li>
     * <li>at most 128 characters</li>
     * </ol>
     * </li>
     * </ul>
     *
     * @param password
     *            Password to check
     * @return list of security rules that are not respected. <br>
     *         EMPTY list == password is valid
     */
    public static Set<PasswordValidationError> checkPasswordStrength(final String password) {
        final Set<PasswordValidationError> violations = new HashSet<>();

        // Check param
        if (password == null || password.trim().isEmpty()) {
            violations.add(PasswordValidationError.EMPTY_PASSWORD);
            return violations;
        }

        // Length checks
        final int minPasswordSize = 10;
        final int maxPasswordSize = 128;
        if (password.length() < minPasswordSize) {
            violations.add(PasswordValidationError.PASSWORD_TOO_SHORT);
        } else if (password.length() > maxPasswordSize) {
            violations.add(PasswordValidationError.PASSWORD_TOO_LONG);
        }

        // Content check
        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean symbol = false;
        for (final char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                upper = true;
            } else if (Character.isLowerCase(ch)) {
                lower = true;
            } else if (Character.isDigit(ch)) {
                digit = true;
            } else {
                symbol = true;
            }
            // This short-circuits the rest of the loop when all criteria are true.
            if (upper && lower && digit && symbol) {
                break;
            }
        }

        // TODO add password patterns checks

        // Set validation errors
        if (!upper) {
            violations.add(PasswordValidationError.NO_UPPER_CASE_CHARACTER);
        }
        if (!lower) {
            violations.add(PasswordValidationError.NO_SMALL_CASE_CHARACTER);
        }
        if (!digit) {
            violations.add(PasswordValidationError.NO_DIGIT);
        }
        if (!symbol) {
            violations.add(PasswordValidationError.NO_SPECIAL_CHARACTER);
        }

        return violations;
    }

    /**
     * Generate a String encoded in Base64 using SHA1PRNG random algorithm.<br>
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom">Java secure random providers</a>.
     *
     * @return salt unique and random String
     * @throws NoSuchAlgorithmException
     *             failed to execute SHA1PRNG random
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        final SecureRandom random = SecureRandom.getInstance(DEFAULT_RANDOM_ALGORITHM);
        final byte[] bSalt = new byte[24];
        random.nextBytes(bSalt);
        return Base64.getEncoder().encodeToString(bSalt);
    }

    /**
     * From a password, a number of iterations and a salt, returns the corresponding digest using a specific algorithm.
     *
     * @param iterationNb
     *            int The number of iterations of the algorithm. If NULL then it will use the default value: {@value #NB_OF_ITERATIONS}
     * @param password
     *            String The password to encrypt. It must be UTF-8 compatible
     * @param salt
     *            String Password's salt. This must be a String encoded in {@link #Base64}. See {@link #generateSalt()}
     * @param algorithm
     *            the hash algorithm to use. See <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest">Java
     *            message digest</a> for the list of supported algorithms. If null, then it will be default one.
     * @return byte[] The digested password
     * @throws NoSuchAlgorithmException
     *             The requested Message Digest algorithm doesn't exist
     * @throws UnsupportedEncodingException
     *             Password cannot be cast into UTF-8
     */
    public static String getHash(final Integer iterationNb, final String password, final String salt, final String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Convert salt into Base64 byte
        final byte[] bSalt = Base64.getDecoder().decode(salt);

        // Load hash algorithm
        MessageDigest digest;
        if (algorithm != null) {
            digest = MessageDigest.getInstance(algorithm);
        } else {
            digest = MessageDigest.getInstance(DEFAULT_HASH_ALGO);
        }
        digest.reset();
        digest.update(bSalt);

        // Compute X iterations. This will slow down attackers.
        int nbOfIterations = NB_OF_ITERATIONS;
        if (iterationNb != null) {
            nbOfIterations = iterationNb;
        }

        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < nbOfIterations; i++) {
            digest.reset();
            hash = digest.digest(hash);
        }
        // Convert array into a String
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * To get the hash algorithm name to save in DB.
     * @param algorithm current algorithm (if any). If 'null' then it will return the default hash algorithm
     * @return corresponding hash algoritm or default one.
     * @throws NoSuchAlgorithmException
     */
    public static String getHashAlgorithm(final String algorithm) throws NoSuchAlgorithmException {
        String algo = algorithm;
        if (algorithm != null) {
            // Ensure that it is a valid algorithm
            MessageDigest.getInstance(algorithm);
        } else {
            algo = DEFAULT_HASH_ALGO;
        }
        return algo;
    }
}
