package eu.daxiongmao.travel.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Application user
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "id", "firstName", "lastName", "langCode", "username", "email", "phoneNumber", "status", "isActive", "activationKey", "emailConfirmationDate", "passwordHash", "passwordSalt", "passwordAlgorithm", "passwordLastChangeDate" })
@EqualsAndHashCode(of = {"username", "email", "passwordSalt", "passwordAlgorithm", "langCode"})
@Entity
@Table(name = "USERS")
public class User extends GenericEntity {

    private static final long serialVersionUID = 20191205L;

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "seqUsers", sequenceName = "SEQ_USERS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsers")
    private Long id;

    /** First name */
    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    /** Last name */
    @Column(name = "SURNAME", length = 200)
    private String lastName;

    /** Preferred language (ex: EN, FR, DE, etc.) */
    @Column(name = "LANG_CODE", nullable = false, length = 2)
    private String langCode;

    /** User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username.
     *  OWASP principles, username must be unique and not case sensitive.
     *  Always in UPPER case for this application
     */
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;
    public void setUsername(String username) {
        if (username != null) {
            this.username = username.trim().toUpperCase();
        } else {
            this.username = null;
        }
    }

    /** User email. MANDATORY. This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application
     *  OWASP principles, email must be unique and not case sensitive
     *  Always in LOWER case for this application */
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.trim().toLowerCase();
        } else {
            this.email = null;
        }
    }

    /** User phone number. If provided it must include the country code. ex: +352 for Luxembourg ; +33 for France */
    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;

    /** User status. MANDATORY. This represents his current status if enabled */
    @Column(name = "STATUS", nullable = false, length = 255)
    private String status;

    /** Activation key. MANDATORY. This is required to confirm the user registration and activate the account */
    @Column(name = "ACTIVATION_KEY", nullable = false, length = 255)
    private String activationKey;

    /** To know when the user confirmed his email - if he did so. This is required for anti-phishing reasons */
    @Column(name = "DATE_EMAIL_CONFIRMED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailConfirmationDate;

    /** User password. This can be let NULL if user wants to validate his email first. Important: like all modern apps, we do NOT store the user password */
    @Column(name = "PASSWORD_HASH", length = 255)
    private String passwordHash;

    /** Security salt. MANDATORY. (random value) that is required to compute the user password hash. Every user have different salts' */
    @Column(name = "PASSWORD_SALT", nullable = false, length = 255)
    private String passwordSalt;

    /** Password hash algorithm. MANDATORY. This is required to compute the password hash. Security can change over time, that is why we must store the algorithm used for each user */
    @Column(name = "PASSWORD_ALGORITHM", nullable = false, length = 50)
    private String passwordAlgorithm;

    /** Date time of the last password change. This is important to notify user to change his password periodically for security reasons */
    @Column(name = "PASSWORD_LAST_CHANGE")
    private Date passwordLastChangeDate;

    public User() {
        super();
    }
}

