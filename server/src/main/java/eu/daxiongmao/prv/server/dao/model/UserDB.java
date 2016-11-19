package eu.daxiongmao.prv.server.dao.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.daxiongmao.prv.server.util.PasswordUtils;

@Entity
@Table(name = "USER")
public class UserDB implements Serializable {

    private static final long serialVersionUID = 4737114756088419932L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", length = 75, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "EMAIL_ADDRESS", length = 255, nullable = false)
    private String email;

    @Column(name = "EMAIL_IS_VALID", nullable = false)
    private Boolean isValidEmail;

    @Column(name = "PASSWORD_SALT", length = 255, nullable = false)
    private String passwordSalt;

    @Column(name = "PASSWORD_ALGO", length = 10, nullable = false)
    private String passwordHashAlgorithm;

    @Column(name = "PASSWORD_HASH", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "PASSWORD_LAST_CHANGE")
    private LocalDate passwordLastChange;

    @Column(name = "ACCOUNT_IS_LOCKED", nullable = false)
    private Boolean isUserLocked;

    @Column(name = "ACCOUNT_IS_ENABLED", nullable = false)
    private Boolean isUserEnable;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<VehicleDB> vehicles;

    /**
     * Default factory, required by JPA.
     */
    public UserDB() {
    }

    /**
     * Constructor to create minimal new user.<br/>
     * By default a new user has the following properties:
     * <ul>
     * <li>Is enable</li>
     * <li>Is NOT lock</li>
     * <li>Email is NOT validated</li>
     * </ul>
     *
     * @param email
     *            user email
     * @param password
     *            original user password. An unique SALT will be generated and then the password will be hash.
     */
    public UserDB(final String email, final String password) {
        setPassword(password);
        this.email = email;
        this.isUserEnable = true;
        this.isUserLocked = false;
        this.isValidEmail = false;
    }

    /**
     * Constructor to create new user with all settings.<br/>
     * By default a new user has the following properties:
     * <ul>
     * <li>Is enable</li>
     * <li>Is NOT lock</li>
     * <li>Email is NOT validated</li>
     * </ul>
     *
     * @param email
     *            user email
     * @param password
     *            original user password. An unique SALT will be generated and then the password will be hash.
     * @param firstName
     *            [optional] user first name
     * @param lastName
     *            [optional] user last name
     */
    public UserDB(final String email, final String password, final String firstName, final String lastName) {
        this(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setPassword(final String newPassword) {
        final String defaultHashAlgo = null;
        try {
            this.passwordSalt = PasswordUtils.generateSalt();
            this.passwordHash = PasswordUtils.getHash(null, newPassword, this.passwordSalt, defaultHashAlgo);
            this.passwordHashAlgorithm = PasswordUtils.getHashAlgorithm(defaultHashAlgo);
            this.passwordLastChange = LocalDate.now();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("failed to set password.", e);
        }
    }

    @Override
    public String toString() {
        final String log = "ID: %s | %s # %s, %s | enabled? %s | locked? %s";
        return String.format(log, this.id, this.email, this.firstName, this.lastName, this.isUserEnable, this.isUserLocked);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getIsValidEmail() {
        return isValidEmail;
    }

    public void setIsValidEmail(final Boolean isValidEmail) {
        this.isValidEmail = isValidEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean getIsUserLocked() {
        return isUserLocked;
    }

    public void setIsUserLocked(final Boolean isUserLocked) {
        this.isUserLocked = isUserLocked;
    }

    public Boolean getIsUserEnable() {
        return isUserEnable;
    }

    public void setIsUserEnable(final Boolean isUserEnable) {
        this.isUserEnable = isUserEnable;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(final String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHashAlgorithm() {
        return passwordHashAlgorithm;
    }

    public void setPasswordHashAlgorithm(final String passwordHashAlgorithm) {
        this.passwordHashAlgorithm = passwordHashAlgorithm;
    }

    public LocalDate getPasswordLastChange() {
        return passwordLastChange;
    }

    public void setPasswordLastChange(final LocalDate passwordLastChange) {
        this.passwordLastChange = passwordLastChange;
    }

    public List<VehicleDB> getVehicles() {
        if (this.vehicles == null) {
            this.vehicles = new ArrayList<>();
        }
        return vehicles;
    }

    public void setVehicles(final List<VehicleDB> vehicles) {
        this.vehicles = vehicles;
    }

    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
