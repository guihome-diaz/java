package eu.daxiongmao.wordpress.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CascadeType;

import lombok.NoArgsConstructor;

/**
 * This represents a WORDPRESS NEXTGEN GALLERY PLUGIN file.
 *
 * @author Guillaume Diaz
 * @since version 1.0 - October 2017
 * @version 1.0
 */
@Entity
@Table(name = "FILE", indexes = { @Index(name = "FILE_IDX_GALLERY_ID", columnList = "GALLERY_ID") })
@NoArgsConstructor
public class File implements Serializable {

        private static final long serialVersionUID = -20171001L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(nullable = false, length = 200, name = "FILE_NAME", unique = false)
        private String filename;
        
        @Column(nullable = true, name = "SIZE", unique = false)
        private Long size;

        /** File timestamp:  this usually the last modification time. */
        @Column(nullable = false, name = "TIMESTAMP", unique = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date timestamp;
        
        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="GALLERY_ID")
        private Gallery gallery;
        
        /** Flag to know if the file is the one with the original size or not (= it has been resized). */
        @Basic
        @Column(nullable = true, name = "IS_ORIGINAL", unique = false)        
        private boolean isOriginalSize = false;
}
