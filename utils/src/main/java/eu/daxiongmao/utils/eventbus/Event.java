package eu.daxiongmao.utils.eventbus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description of an application event.<br>
 * This is a custom and quick implementation of the event programming pattern.
 *
 * @author Guillaume Diaz
 * @version 1.0 - 2017/01
 */
public class Event implements Serializable {

    private static final long serialVersionUID = 201701071455L;

    private final String eventName;

    /**
     * {@link #getProperties()}
     */
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * @param eventName
     *            event name
     */
    public Event(final String eventName) {
        this.eventName = eventName;
    }

    /**
     * @param eventName
     *            event name
     * @param properties
     *            {properties, values} associated to the current event.
     *            <ul>
     *            <li>Key: property name</li>
     *            <li>Value: property value, as a general Object. You must cast it to the expected Class before using it.</li>
     *            </ul>
     */
    public Event(final String eventName, final Map<String, Object> properties) {
        this.eventName = eventName;
        if (properties != null) {
            this.properties.putAll(properties);
        }
    }

    /**
     * @return returns {properties, values} associated to the current event.
     *         <ul>
     *         <li>Key: property name</li>
     *         <li>Value: property value, as a general Object. You must cast it to the expected Class before using it.</li>
     *         </ul>
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * To retrieve a particular property.
     *
     * @return property value or NULL.<br>
     *         Don't forget to cast the returned object to the expected type
     */
    public Object getProperty(final String propertyName) {
        Object target = null;
        if (properties.containsKey(propertyName)) {
            target = properties.get(propertyName);
        }
        return target;
    }

    /**
     * To add a property.
     * 
     * @param propertyName
     *            name of the new property
     * @param propertyValue
     *            value of the new property
     */
    public void addProperty(final String propertyName, final Object propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    /**
     * @return event name
     */
    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        log.append("Event name: ").append(eventName);
        if (!properties.isEmpty()) {
            log.append(String.format("%nList of properties name available:"));
            for (final String propName : properties.keySet()) {
                log.append(String.format("%n  * %s", propName));
            }
        }
        return log.toString();
    }
}
