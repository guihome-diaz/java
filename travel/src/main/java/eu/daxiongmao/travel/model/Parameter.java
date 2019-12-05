package eu.daxiongmao.travel.model;

import lombok.*;

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
}
