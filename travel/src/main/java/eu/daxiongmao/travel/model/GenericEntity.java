package eu.daxiongmao.travel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Generic entity that contains attributes for all application's objects.
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "isActive", "creationDate", "modificationDate", "version" })
@MappedSuperclass
public class GenericEntity implements Serializable {

    private static final long serialVersionUID = 20191205L;

    @Column(name = "CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Version
    @Column(name = "VERSION", nullable = false)
    private long version = 0L;

    @Column(name = "MODIFICATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate = new Date();

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

}
