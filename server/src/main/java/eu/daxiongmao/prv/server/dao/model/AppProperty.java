package eu.daxiongmao.prv.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APP_PROPERTY")
public class AppProperty implements Serializable {

    private static final long serialVersionUID = 4780831904546954664L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NAME", length = 255, nullable = false, unique = true)
    private String key;

    @Column(name = "VALUE", length = 255, nullable = false)
    private String value;

    @Column(name = "COMMENTS", length = 255, nullable = true)
    private String description;

    public AppProperty() {
    }

    public AppProperty(final String key, final String value, final String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %3s | %s = %s", id, key, value);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
