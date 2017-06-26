package eu.daxiongmao.wordpress.ui.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This represent an application property that user can edit in CONFIGURATION screen.<br>
 * This is the FXML table-view object
 *
 * @author Guillaume Diaz
 * @version 1.0 - May 2017
 */
public class AppPropertyFx {

    private SimpleIntegerProperty id;

    private SimpleStringProperty key;

    private SimpleStringProperty value;

    private SimpleStringProperty description;

    public AppPropertyFx(final String key, final String value, final String description) {
        id = new SimpleIntegerProperty();
        this.key = new SimpleStringProperty(key);
        if (value == null) {
            this.value = new SimpleStringProperty("");
        } else {
            this.value = new SimpleStringProperty(value);
        }
        if (description == null) {
            this.description = new SimpleStringProperty("");
        } else {
            this.description = new SimpleStringProperty(description);
        }
    }

    public AppPropertyFx(final Integer id, final String key, final String value, final String description) {
        this(key, value, description);
        if (id != null) {
            this.id.set(id);
        }
    }

    public AppPropertyFx() {
        this("", "", "");
    }

    public void setKey(final String key) {
        this.key.set(key);
    }

    public void setValue(final String value) {
        this.value.set(value);
    }

    public String getKey() {
        return key.get();
    }

    public String getValue() {
        return value.get();
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(final String description) {
        this.description.set(description);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(final Integer id) {
        this.id.set(id);
    }
}
