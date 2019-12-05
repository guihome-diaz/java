package eu.daxiongmao.travel.model;

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

    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "SURNAME", length = 200)
    private String lastName;

    @Column(name = "LANG_CODE", nullable = false, length = 2)
    private String langCode;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;

    @Column(name = "STATUS", nullable = false, length = 255)
    private String status;

    @Column(name = "ACTIVATION_KEY", nullable = false, length = 255)
    private String activationKey;

    @Column(name = "DATE_EMAIL_CONFIRMED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailConfirmationDate;

    @Column(name = "PASSWORD_HASH", length = 255)
    private String passwordHash;

    @Column(name = "PASSWORD_SALT", nullable = false, length = 255)
    private String passwordSalt;

    @Column(name = "PASSWORD_ALGORITHM", nullable = false, length = 50)
    private String passwordAlgorithm;

    @Column(name = "PASSWORD_LAST_CHANGE")
    private Date passwordLastChangeDate;

    public User() {
        super();
    }
}

