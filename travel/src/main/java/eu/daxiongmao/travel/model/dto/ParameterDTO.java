package eu.daxiongmao.travel.model.dto;

import eu.daxiongmao.travel.utils.ParameterUtils;
import lombok.*;


/**
 * Application parameter
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "paramName", "paramValue", "description" })
@EqualsAndHashCode(of = {"paramName", "paramValue"})
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDTO {

    private static final long serialVersionUID = 20191205L;

    private String paramName;
    private String paramValue;
    private String description;

    /** Boolean flag. MANDATORY. "1" to use the object, "0" to block usage */
    private Boolean isActive;

    /**
     * To convert a given String value into a particular output type
     * @param clazz output class to convert the String into
     * @param <T> String class
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     */
    public <T> T getValue(final Class<T> clazz) {
        return ParameterUtils.getValue(this.paramValue, clazz);
    }
}
