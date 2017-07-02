package eu.daxiongmao.wordpress.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This represents an application's property.
 *
 * @author Guillaume Diaz
 * @since version 1.0 - June 2017
 * @version 1.0
 */
@Entity
@Table(name = "APP_PROPERTY", indexes = { @Index(name = "APP_PROP_IDX_KEY", columnList = "KEY", unique = true) })
@NoArgsConstructor
public @Data class AppProperty implements Serializable {

    private static final long serialVersionUID = -20170625L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 200, name = "KEY", unique = true)
    private String key;

    @Column(nullable = true, length = 255, name = "VALUE", unique = false)
    private String value;

    @Column(nullable = true, length = 2500, name = "DESCRIPTION", unique = false)
    private String description;

    public AppProperty(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public AppProperty(final String key, final String value, final String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }
    
    public Integer getValueAsInteger() {
    	Integer result = null;
    	if (this.value != null) {
    		result = Integer.parseInt(this.value);
    	}
    	return result;
    }
    
    public Boolean getValueAsBoolean() {
    	Boolean result = null;
    	if (this.value != null) {
    		result = Boolean.parseBoolean(this.value);
    	}
    	return result;
    }
    
    public String toHtmlString() {
        StringBuilder log = new StringBuilder();
        log.append("Key: ").append(key).append("<br>");
        log.append("Value: ").append(value).append("<br>");
        return log.toString();
    }
    
}
