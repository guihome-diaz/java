package eu.daxiongmao.training.vertx.wiki;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import io.vertx.core.Future;
import org.apache.logging.log4j.core.appender.routing.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>Main verticle of the application</h2>
 * <p>This is the Verticle that handles application's startup.<br>
 * To start the application, we need to perform a 2-phases initialization:</p>
 * <ul>
 *     <li>First, we need to establish a JDBC database connection, and also make sure that the database schema is in place</li>
 *     <li>Then, we need to start a HTTP server for the web application</li>
 *</ul>
 * <p>Each phase can fail (e.g., the HTTP server TCP port is already being used), and they should not run in parallel as the web application code first needs the database access to work.</p>
 * <p>To make our code <i>cleaner</i> we will define 1 method per phase, and adopt a pattern of returning a <code>future</code> object to notify when each of the phases completes, and whether it did so successfully or not. See:</p>
 * <ul>
 *     <li><code>private Future&lt;Void&gt; prepareDatabase() { ..}</code></li>
 *     <li><code>private Future&lt;Void&gt; startHttpServer() { ..}</code></li>
 * </ul>
 * <p>To establish a list of required steps (= startup chain), each step returns a promise that trigger the next call.
 * See main start point: <code>public void start(Promise&lt;Void&gt; promise) { .. }</code>
 * </p>
 * @author Guillaume Diaz
 * @since 2019-11 - project creation
 */
@Slf4j
public class MainVerticle extends AbstractVerticle {

    protected static final int SERVER_PORT = 13080;

    @Override
    public void start(Promise<Void> promise) throws Exception {
      Future<Void> startupSteps = startHttpServer();
      // Return startup steps' result
      startupSteps.setHandler(promise);
    }


    private Future<Void> startHttpServer() {
        Promise<Void> promise = Promise.promise();

        // ****** Declare application's routes ******
        Router router = Router.router(vertx);
        // TODO add routes
        //      Routes have their own handlers, and they can be defined by URL and/or by HTTP method.
        //      It is a good idea to reference private methods for each route
        //      Note that URLs can be parametric: /wiki/:page will match a request like /wiki/Hello, in which case a "page" parameter will be available with value Hello

        // *** GETS
        // Application default's reply (path "/")
        router.route("/").handler( routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("Content-Type", "text/html")
                    .end("<p>Hello, welcome on VertX training.<br> This wiki is based upon <a href='https://github.com/vert-x3/vertx-guide-for-java-devs'>VertX guide for java developers</a>. Only changes are:</p>" +
                        "<ul>" +
                        "   <li><code>Log4j2</code> instead of <code>Logback</code></li>" +
                        "   <li>Usage of <code>Junit5</code></li>" +
                        "</ul>");
        });

        // *** POSTS
        // First handler for POST requests
        // (i) This handler has no dedicated route => all HTTP POST requests go through it before reaching the destination
        // {io.vertx.ext.web.handler.BodyHandler} decodes the body from the HTTP requests (e.g., form submissions), which can then be manipulated as Vert.x buffer objects.
        router.post().handler(BodyHandler.create());
        // ********************************************

        // ****** Create HTTP server ******
        // VertX starts a Netty by default
        HttpServer server = vertx.createHttpServer();
        // Configure server
        server
            // apply routes
            .requestHandler(router)
            // bind server to port in async manner
            .listen(SERVER_PORT, asyncResult -> {
                if (asyncResult.succeeded()) {
                    // All is ok, send success
                    log.info("HTTP server running on port " + server.actualPort());
                    promise.complete();
                } else {
                    // Something went wrong, forward error
                    log.error("Could not start HTTP server", asyncResult.cause());
                    promise.fail(asyncResult.cause());
                }
            });
        // ********************************************

        // Return async promise: caller will be notify once server has started or failed to do so
        return promise.future();
    }

}
