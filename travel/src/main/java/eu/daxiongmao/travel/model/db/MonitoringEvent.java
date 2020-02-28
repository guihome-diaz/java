package eu.daxiongmao.travel.model.db;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Monitoring event: not functional data that aims to improve overall performances, stability and success rate.
 * It is also useful to detect the 20% of the code that brings 80% of the value.
 * @version 1.0 - 2020/03
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "eventName", "eventType", "thirdParty", "eventTime", "executionTimeInMs", "executionResult", "item1", "item2", "item3"})
@EqualsAndHashCode(of = {"eventName", "eventType", "thirdParty", "eventTime", "executionTimeInMs" })
@Entity
@Table(name = "MONITORING_EVENTS")
public class MonitoringEvent implements Serializable {

    private static final long serialVersionUID = 20200301;

    @Id
    @Column(name = "EVENT_ID")
    @SequenceGenerator(name = "seqMonitoringEvents", sequenceName = "SEQ_MONITORING_EVENTS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMonitoringEvents")
    private Long id;

    /** Nature of the event (performance,functional, etc.) */
    @NotBlank
    @Max(50)
    @Column(name = "EVENT_NATURE", nullable = false, length = 50)
    private String eventName;

    /** Type of the event to track [web-service,database,email,file,inner algorithms] */
    @NotBlank
    @Max(50)
    @Column(name = "EVENT_TYPE", nullable = false, length = 50)
    private String eventType;

    /** Date-time of the event */
    @Column(name = "EVENT_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime = new Date();

    /** Name of the 3rd party (Google, Facebook, OVH, etc.) */
    @Max(100)
    @Column(name = "THIRD_PARTY", length = 100)
    private String thirdParty;

    /** Execution duration in milliseconds (ms) */
    @Column(name = "EXECUTION_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private long executionTimeInMs;

    /** To track down success and errors. This is useful to compare timing issues on failure (SUCCESS, FAILURE) */
    @Max(50)
    @Column(name = "EXECUTION_RESULT", nullable = false, length = 50)
    private String executionResult;

    /** Generic column to organize report contents. PERFORMANCE report: item 1 = Class name */
    @Max(255)
    @Column(name = "ITEM_1", length = 255)
    private String item1;

    /** Generic column to organize report contents. PERFORMANCE report: item 2 = Method name (arguments are not provided) */
    @Max(255)
    @Column(name = "ITEM_2", length = 255)
    private String item2;

    /** Generic column to organize report contents. PERFORMANCE report: item 3 = Functional workflow */
    @Max(255)
    @Column(name = "ITEM_3", length = 255)
    private String item3;

    /** Free text field to describe the situation. Might be useful sometimes (content truncate at 4000) */
    @Max(4000)
    @Column(name = "COMMENTS", length = 4000)
    private String comments;

}
