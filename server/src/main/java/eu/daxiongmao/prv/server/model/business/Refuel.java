package eu.daxiongmao.prv.server.model.business;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Refuel {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="Europe/Luxembourg")
    private Date date;

    private float volume;

    private float distance;

    private DrivingType type;

    private String comments;

    private Long vehicleId;

    public Refuel() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(final Long vehicleId) {
        this.vehicleId = vehicleId;
    }

}
