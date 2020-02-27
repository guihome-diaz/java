package eu.daxiongmao.travel.model.dto;

import eu.daxiongmao.travel.utils.StringToClassUtils;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;


/**
 * Application parameter
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "paramName", "paramValue", "paramType", "description" })
@EqualsAndHashCode(of = {"paramName", "paramValue", "paramType"})
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDTO {

    private static final long serialVersionUID = 20191205L;

    @NotBlank
    @Max(200)
    private String paramType;

    @NotBlank
    @Max(100)
    private String paramName;

    @NotBlank
    @Max(255)
    private String paramValue;

    @Max(1500)
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
        return StringToClassUtils.getValue(this.paramValue, clazz);
    }
}
