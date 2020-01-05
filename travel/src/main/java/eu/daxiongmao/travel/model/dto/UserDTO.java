package eu.daxiongmao.travel.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Application user
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "firstName", "lastName", "langCode", "username", "email", "phoneNumber", "status", "isActive", "emailConfirmationDate", "passwordLastChangeDate" })
@EqualsAndHashCode(of = {"username", "email", "langCode"})
public class UserDTO {

    private static final long serialVersionUID = 20191205L;

    /** First name */
    private String firstName;

    /** Last name */
    private String lastName;

    /** Preferred language (ex: EN, FR, DE, etc.) */
    private String langCode;

    /** User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username
     * It is always in UPPER case */
    private String username;
    public void setUsername(String username) {
        if (username != null) {
            this.username = username.trim().toUpperCase();
        } else {
            this.username = null;
        }
    }

    /** User email. MANDATORY.
     * This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application.
     * It is always in LOWER case
     */
    private String email;
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.trim().toLowerCase();
        } else {
            this.email = null;
        }
    }

    /** User phone number. If provided it must include the country code. ex: +352 for Luxembourg ; +33 for France */
    private String phoneNumber;

    /** User status. MANDATORY. This represents his current status if enabled */
    private String status;

    /** To know when the user confirmed his email - if he did so. This is required for anti-phishing reasons */
    private Date emailConfirmationDate;

    /** Date time of the last password change. This is important to notify user to change his password periodically for security reasons */
    private Date passwordLastChangeDate;

    /** Boolean flag. MANDATORY. "1" to use the object, "0" to block usage */
    private Boolean isActive;

    /** Object version. This is incremented at each operation */
    private long version;

    public UserDTO() {
        super();
    }
}

