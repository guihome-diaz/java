package eu.daxiongmao.travel.web.dto;

import eu.daxiongmao.travel.model.GenericEntity;
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
@ToString(callSuper = true, of = { "id", "firstName", "lastName", "langCode", "username", "email", "phoneNumber", "status", "isActive", "activationKey", "emailConfirmationDate", "passwordHash", "passwordSalt", "passwordAlgorithm", "passwordLastChangeDate" })
@EqualsAndHashCode(of = {"username", "email", "passwordSalt", "passwordAlgorithm", "langCode"})
public class UserDTO extends GenericEntity {

    private static final long serialVersionUID = 20191205L;

    /** First name */
    private String firstName;

    /** Last name */
    private String lastName;

    /** Preferred language (ex: EN, FR, DE, etc.) */
    private String langCode;

    /** User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username */
    private String username;

    /** User email. MANDATORY. This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application */
    private String email;

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

