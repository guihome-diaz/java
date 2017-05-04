package eu.daxiongmao.prv.wordpress.model.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * This represent an application property that user can edit in CONFIGURATION screen.<br>
 * This is the FXML table-view object
 *
 * @author Guillaume Diaz
 * @version 1.0 - May 2017
 */
public class Property {

    private SimpleStringProperty key;

    private SimpleStringProperty value;

    public Property(final String key, final String value) {
        this.key = new SimpleStringProperty(key);
        if (value == null) {
            this.value = new SimpleStringProperty("");
        } else {
            this.value = new SimpleStringProperty(value);
        }
    }

    public Property() {
        this("", "");
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
}
