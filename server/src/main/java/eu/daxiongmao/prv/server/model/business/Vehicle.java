package eu.daxiongmao.prv.server.model.business;

public class Vehicle {

    private Long id;

    private String brand;

    private String model;

    private String serie;

    private Integer yearMake;

    private Long userId;

    public Vehicle() {
    }

    @Override
    public String toString() {
        return String.format("ID: %5s | %s, %s %s | Year make: %s", id, brand, model, serie, yearMake);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public Integer getYearMake() {
        return yearMake;
    }

    public void setYearMake(final Integer yearMake) {
        this.yearMake = yearMake;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(final String serie) {
        this.serie = serie;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

}
