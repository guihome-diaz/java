package eu.daxiongmao.prv.server.model.business;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<Vehicle> vehicles;

    public User() {
    }

    /**
     * New user
     * @param email
     *            user email
     * @param firstName
     *            [optional] user first name
     * @param lastName
     *            [optional] user last name
     */
    public User(final String email, final String firstName, final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        final String log = "ID: %s | %s # %s, %s";
        return String.format(log, this.id, this.email, this.firstName, this.lastName);
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<Vehicle> getVehicles() {
        if (this.vehicles == null) {
            this.vehicles = new ArrayList<>();
        }
        return vehicles;
    }

    public void setVehicles(final List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

}
