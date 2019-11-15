package eu.daxiongmao.training.vertx.wiki;

import eu.daxiongmao.training.vertx.wiki.config.HttpServerConfiguration;
import eu.daxiongmao.training.vertx.wiki.config.JdbcConfiguration;
import eu.daxiongmao.training.vertx.wiki.config.dto.DbConfigDTO;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>Main verticle of the application</h2>
 * <p>This is the Verticle that handles <strong>startup</strong> and <strong>declares other verticles</strong>.</p>
 * <h2>startup</h2>
 * <p>To start the application, we need to perform a 2-phases initialization:</p>
 * <ul>
 *     <li>First, we need to establish a JDBC database connection, and also make sure that the database schema is in place</li>
 *     <li>Then, we need to start a HTTP server for the web application</li>
 * </ul>
 * <p>Each phase can fail (e.g., the HTTP server TCP port is already being used), and they should not run in parallel as the web application code first needs the database access to work.</p>
 * <p>To make our code <i>cleaner</i> we will define 1 method per phase, and adopt a pattern of returning a <code>future</code> object to notify when each of the phases completes, and whether it did so successfully or not. See:</p>
 * <ul>
 *     <li><code>private Future&lt;Void&gt; prepareDatabase() { ..}</code></li>
 *     <li><code>private Future&lt;Void&gt; startHttpServer() { ..}</code></li>
 * </ul>
 * <p>To establish a list of required steps (= startup chain), each step returns a promise that trigger the next call.
 * See main start point: <code>public void start(Promise&lt;Void&gt; promise) { .. }</code>
 * </p>
 *
 * <h2>HTTP routing</h2>
 * <p>The routes are defined for the HTTP server verticle.</p>
 *
 * @author Guillaume Diaz
 * @since 2019-11 - project creation
 */
@Slf4j
public class MainVerticle extends AbstractVerticle {


    @Override
    public void start(Promise<Void> promise) throws Exception {
        // Create startup chain by chaining steps
        //   ==> Start DB, then start HTTP server
        // if DB fails then the HTTP server will NOT start
        // (i) Note the usage of "Future.compose(..)" to chain actions
        Future<Void> startupSteps = initDatabaseConnection().compose(nextStep -> initHttpServer());

        // Return overall startup steps' result
        startupSteps.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("Application startup complete - ready to work!");
                promise.complete();
            } else {
                log.error("Failed to start application", asyncResult.cause());
                promise.fail(asyncResult.cause());
            }
        });
    }


    private Future<Void> initHttpServer() {
        // Declare promise (= callback object)
        final Promise<Void> promise = Promise.promise();

        // Create and start HTTP inside current verticle
        final HttpServerConfiguration httpServer = new HttpServerConfiguration();
        httpServer.start(promise, vertx);

        // Caller will be notify once server has started or failed to do so (see worker class)
        return promise.future();
    }

    private Future<Void> initDatabaseConnection() {
        Promise<Void> promise = Promise.promise();

        // Create DB configuration
        final DbConfigDTO dbConfig = new DbConfigDTO();
        dbConfig.setDbUrl("jdbc:hsqldb:file:db/wiki");
        dbConfig.setDbDriver("org.hsqldb.jdbcDriver");
        dbConfig.setMaxPoolSize(10);

        // Connect to DB
        final JdbcConfiguration jdbcConnection = new JdbcConfiguration();
        jdbcConnection.connect(promise, vertx, dbConfig);

        // Caller will be notify once server has started or failed to do so (see worker class)
        return promise.future();
    }

}
