package eu.daxiongmao.prv.server.dao.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.daxiongmao.prv.server.model.business.DrivingType;

@Entity
@Table(name = "REFUEL")
public class RefuelDB implements Serializable {

    private static final long serialVersionUID = -4113194236246165965L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID", nullable = false)
    private VehicleDB vehicle;

    @Column
    private LocalDate date;

    @Column(name = "VOLUME", precision = 2, nullable = false)
    private float volume;

    @Column(name = "DISTANCE", precision = 2, nullable = false)
    private float distance;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = true)
    private DrivingType type;

    @Column(name = "COMMENTS", nullable = true)
    private String comments;

    public RefuelDB() {
    }

    public RefuelDB(final VehicleDB vehicle, final LocalDate date, final float volume, final float distance, final DrivingType type, final String comments) {
        this.vehicle = vehicle;
        if (date == null) {
            this.date = LocalDate.now();
        } else {
            this.date = date;
        }
        this.volume = volume;
        this.distance = distance;
        this.type = type;
        this.comments = comments;
    }

    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        log.append(String.format("Id: %5s | Date: %s | Volume: %2.2f | Distance: %4.2f | Driving type: %8s", id, date, volume, distance, type));
        if (comments != null) {
            log.append(" | comments: ").append(comments);
        }
        return log.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(final float volume) {
        this.volume = volume;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(final float distance) {
        this.distance = distance;
    }

    public DrivingType getType() {
        return type;
    }

    public void setType(final DrivingType type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public VehicleDB getVehicle() {
        return vehicle;
    }

    public void setVehicle(final VehicleDB vehicle) {
        this.vehicle = vehicle;
    }

}
