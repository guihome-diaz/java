package eu.daxiongmao.prv.server.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "VEHICLE")
public class VehicleDB implements Serializable {

    private static final long serialVersionUID = 5742339243846111555L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "BRAND", length = 50, nullable = false)
    private String brand;

    @Column(name = "MODEL", length = 100, nullable = false)
    private String model;

    @Column(name = "SERIE", length = 100, nullable = false)
    private String serie;

    @Column(name = "YEAR_MAKE", nullable = false)
    private Integer yearMake;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private List<RefuelDB> refuels;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserDB user;

    public VehicleDB() {
    }

    public VehicleDB(final UserDB user, final String brand, final String model, final String serie, final int yearMake) {
        this.user = user;
        this.brand = brand;
        this.model = model;
        this.serie = serie;
        this.yearMake = yearMake;
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

    public List<RefuelDB> getRefuels() {
        if (this.refuels == null) {
            this.refuels = new ArrayList<>();
        }
        return refuels;
    }

    public void setRefuels(final List<RefuelDB> refuels) {
        if (refuels != null) {
            this.refuels = refuels;
        }
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(final String serie) {
        this.serie = serie;
    }

    public UserDB getUser() {
        return user;
    }

    public void setUser(final UserDB user) {
        this.user = user;
    }

}
