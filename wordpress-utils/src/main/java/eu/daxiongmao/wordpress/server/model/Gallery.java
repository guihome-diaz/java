package eu.daxiongmao.wordpress.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.NoArgsConstructor;

/**
 * This represents a WORDPRESS NEXTGEN GALLERY folder.
 *
 * @author Guillaume Diaz
 * @since version 1.0 - October 2017
 * @version 1.0
 */
@Entity
@Table(name = "GALLERY", indexes = { @Index(name = "APP_PROP_IDX_KEY", columnList = "KEY", unique = true) })
@NoArgsConstructor
public class Gallery implements Serializable {

    private static final long serialVersionUID = -20171001L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false, length = 250, name = "NAME", unique = false)
    private String name;
    
    /** File timestamp:  this usually the last modification time. */
    @Column(nullable = false, name = "TIMESTAMP", unique = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    /** List of files belonging to that particular gallery. */
    @OneToMany(mappedBy="gallery")
    private List<File> files;
    

}
