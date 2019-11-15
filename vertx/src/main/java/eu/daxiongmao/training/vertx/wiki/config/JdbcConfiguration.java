package eu.daxiongmao.training.vertx.wiki.config;

import eu.daxiongmao.training.vertx.wiki.config.dto.DbConfigDTO;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import lombok.extern.slf4j.Slf4j;

/**
 * To configure and start a JDBC connection inside a Verticle.
 * @author VertX tutorial - https://vertx.io/docs/guide-for-java-devs/
 * @author Guillaume Diaz (student)
 * @version 1.0 - 2019/11
 */
@Slf4j
public class JdbcConfiguration {

    /**
     * To connect to a database.<br>
     *     Please note that database connection will be <strong>shared</strong> with all verticles known to given Vert.X core context
     * @param promise callback to notify of JDBC connection ok | failure
     * @param vertx Entry point to Vert.X core, this is required to create JDBC connection.
     * @param dbConfig database settings
     */
    public void connect(final Promise<Void> promise, final Vertx vertx, final DbConfigDTO dbConfig) {
        // Transform given configuration into VertX format
        final JsonObject jsonDbSettings = getDatabaseConfiguration(dbConfig);

        // Create JDBC object that will be shared will all verticles
        //   ==> all verticles will access the DB through the same connection
        final JDBCClient dbClient = JDBCClient.createShared(vertx, jsonDbSettings);

        // Do connection
        establishConnection(promise, dbConfig, dbClient);
    }

    /**
     * To really perform database connection
     * @param promise callback to notify of JDBC connection ok | failure
     * @param dbConfig database settings
     * @param dbClient JDBC client
     */
    private void establishConnection(final Promise<Void> promise, final DbConfigDTO dbConfig, final JDBCClient dbClient) {
        dbClient.getConnection(asyncResult -> {
           if (asyncResult.succeeded()) {
               log.info("Successful connection to database: {}", dbConfig.getDbUrl());
               promise.complete();
           } else {
               log.info("Failed to connect to database: {}", dbConfig.getDbUrl(), asyncResult.cause());
               promise.fail(asyncResult.cause());
           }
        });
    }

    /**
     * To cast object configuration to VertX JSON
     * @param dbConfig database settings
     * @return DB settings in JSON format
     */
    private JsonObject getDatabaseConfiguration(final DbConfigDTO dbConfig) {
        return new JsonObject().put("url", dbConfig.getDbUrl())
                               .put("driver_class", dbConfig.getDbDriver())
                               .put("max_pool_size", dbConfig.getMaxPoolSize());
    }

}
