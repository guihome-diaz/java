package eu.daxiongmao.training.vertx.wiki.config.dto;

import lombok.*;

/**
 * Database connection settings
 * @author Guillaume Diaz
 * @version 1.0 - 2019/11
 */
@Getter
@Setter
@EqualsAndHashCode(of = { "dbUrl", "dbDriver", "maxPoolSize" })
@ToString(of = { "dbUrl", "dbDriver", "maxPoolSize" })
@NoArgsConstructor
public class DbConfigDTO {

    /** database URL (ex: jdbc:hsqldb:file:db/wiki) */
    private String dbUrl;

    /** driver class to use (ex: org.hsqldb.jdbcDriver) */
    private String dbDriver;

    /** number of concurrent connections */
    private int maxPoolSize;
}
