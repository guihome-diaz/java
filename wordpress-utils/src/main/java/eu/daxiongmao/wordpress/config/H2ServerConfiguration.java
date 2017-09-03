package eu.daxiongmao.wordpress.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * To configure the H2 console.<br>
 * According to the application's property this will start some TCP socket for 3rd party SQL client and|or an embedded web-server with the H2 console.
 *
 * @author TechDev (https://techdev.de/querying-the-embedded-h2-database-of-a-spring-boot-application/)
 * @author Guillaume DIAZ
 * @version 1.1 - adjustments of the original TechDev code to work with Spring boot [adding spring metadatas and code comments]
 */
@Configuration
public class H2ServerConfiguration {

    // TCP port for remote connections, default 9092
    @Value("${h2.tcp.port:9092}")
    private String h2TcpPort;

    // Web port, default 8082
    @Value("${h2.web.port:8082}")
    private String h2WebPort;

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.<br>
     * Connect example:
     * <ul>
     * <li>jdbc:h2:tcp://localhost:9092/mem:testdb</li>
     * <li>jdbc:h2:tcp://localhost:9092/file:/home/guillaume/daxiongmao/wordpress-utils/wordpress-utils.db</li>
     * </ul>
     * Default values: username "sa", password empty
     *
     * @return TCP access to the H2 database
     * @throws SQLException
     *             database error # cannot start TCP server
     */
    @Bean
    @ConditionalOnExpression("${h2.tcp.enabled:false}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }

    /**
     * Web console for the embedded h2 database.<br>
     * Go to <a href="http://localhost:8082">http://localhost:8082</a> to access the H2 web-console.
     * <ul>
     * <li>jdbc:h2:mem:testdb</li>
     * <li>jdbc:h2:file:/home/guillaume/daxiongmao/wordpress-utils/wordpress-utils.db</li>
     * </ul>
     * Default values: username "sa", password empty
     *
     * @return Web access to the H2 database
     * @throws SQLException
     *             database error # cannot start WEB server
     */
    @Bean
    @ConditionalOnExpression("${h2.web.enabled:true}")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
    }
}
