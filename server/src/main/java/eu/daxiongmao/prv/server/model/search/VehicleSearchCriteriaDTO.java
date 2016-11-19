package eu.daxiongmao.prv.server.model.search;

public class VehicleSearchCriteriaDTO extends AbstractSearchRequestDTO {

    private Long id;

    private String brand;

    private String model;

    private String serie;

    private Integer yearMake;

    private Integer userId;

    public VehicleSearchCriteriaDTO() {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }
}
