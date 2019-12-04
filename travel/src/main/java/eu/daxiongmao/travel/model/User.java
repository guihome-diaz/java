package eu.daxiongmao.travel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "FIRST_NAME", nullable = "true", length = "100")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = "true", length = "200")
    private String lastName;
}
