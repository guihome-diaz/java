package eu.daxiongmao.travel.model.db;

import eu.daxiongmao.travel.utils.StringToClassUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
@ToString(callSuper = true, of = { "paramName", "paramValue", "paramType", "description", "isSensitive" })
@EqualsAndHashCode(of = {"paramName", "paramValue", "paramType"})
@Entity
@Table(name = "PARAMETERS", indexes = {
        @Index(name = "PARAMS_PARAM_NAME_IDX", unique = true, columnList = "PARAM_NAME"),
        @Index(name = "PARAMS_ACTIVE_PARAM_IDX", columnList = "PARAM_NAME, IS_ACTIVE"),
        @Index(name = "PARAMS_ACTIVE_IDX", columnList = "IS_ACTIVE")
})
public class Parameter extends GenericEntity {

    private static final long serialVersionUID = 20191205L;

    @Id
    @Column(name = "PARAM_ID")
    @SequenceGenerator(name = "seqParameters", sequenceName = "SEQ_PARAMETERS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParameters")
    private Long id;

    @NotBlank
    @Max(100)
    @Column(name = "PARAM_NAME", nullable = false, length = 100)
    private String paramName;

    @NotBlank
    @Max(255)
    @Column(name = "PARAM_VALUE", nullable = false, length = 255)
    private String paramValue;

    /** Parameter type (java class, full qualified name: java.lang.String, java.lang.Integer, custom enum etc.) */
    @NotBlank
    @Max(200)
    @Column(name = "PARAM_TYPE", nullable = false, length = 200)
    private String paramType;

    /** Boolean flag. MANDATORY. "0" if the information can be displayed, "1" if it is confidential (ex: password, secure path, etc.) */
    @Column(name = "IS_SENSITIVE", nullable = false)
    private Boolean isSensitive = false;

    @Max(1500)
    @Column(name = "DESCRIPTION", length = 1500)
    private String description;

    public Parameter() {
        super();
    }

    /**
     * Constructor for jUnit tests
     * @param paramName param name
     * @param paramValue param value
     * @param paramType class, full qualified name
     */
    Parameter(String paramType, String paramName, String paramValue) {
        this.paramType = paramType;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    /**
     * To convert a given String value into a particular output type (according to registered type)
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     * @throws ClassCastException requested class does not exists
     */
    public Object getValue() {
        return StringToClassUtils.getValue(this.paramValue, this.paramType);
    }
}
