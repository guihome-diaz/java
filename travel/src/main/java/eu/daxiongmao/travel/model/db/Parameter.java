package eu.daxiongmao.travel.model.db;

import eu.daxiongmao.travel.utils.ParameterUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


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
@Entity
@Table(name = "PARAMETERS")
public class Parameter extends GenericEntity {

    private static final long serialVersionUID = 20191205L;

    @Id
    @Column(name = "PARAM_ID")
    @SequenceGenerator(name = "seqParameters", sequenceName = "SEQ_PARAMETERS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParameters")
    private Long id;

    @Column(name = "PARAM_NAME", nullable = false, length = 100)
    private String paramName;

    @Column(name = "PARAM_VALUE", nullable = false, length = 255)
    private String paramValue;

    @Column(name = "DESCRIPTION", length = 1500)
    private String description;

    public Parameter() {
        super();
    }

    /**
     * Constructor for jUnit tests
     * @param paramName param name
     * @param paramValue param value
     */
    Parameter(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

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
