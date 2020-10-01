package eu.daxiongmao.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Application user
 * @version 1.0
 * @since 2020-10
 * @author Guillaume Diaz
 */
@Data
@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(name = "user_unq_identity", columnNames = {"first_name", "last_name"})
})
public class User implements Serializable {

    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "seq_user", allocationSize = 1)
    @GeneratedValue(generator = "userSeqGen")
    private Long userId;

    @NotBlank
    @Size(max = 200)
    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;

    @NotBlank
    @Size(max = 250)
    @Column(name = "last_name", nullable = false, length = 250)
    private String lastName;

    @Email
    @NotBlank
    @Size(max = 250)
    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

}
