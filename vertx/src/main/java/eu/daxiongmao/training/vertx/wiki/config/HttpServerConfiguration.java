package eu.daxiongmao.training.vertx.wiki.config;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * To configure and start an HTTP server inside a Verticle.
 * @author VertX tutorial - https://vertx.io/docs/guide-for-java-devs/
 * @author Guillaume Diaz (student)
 * @version 1.0 - 2019/11
 * @since VertX training part 1
 */
@Slf4j
public class HttpServerConfiguration {

    public static final int SERVER_PORT = 13080;

    /**
     * To start an HTTP server
     * @param promise callback to notify of HTTP server start | failure
     * @param vertx Entry point to Vert.X core, this is required to start the HTTP server
     */
    public void start(final Promise<Void> promise, final Vertx vertx) {
        // Get routes
        final Router routes = configureRoutes(vertx);
        // Start server
        startHttpServer(promise, vertx, routes);
    }

    /**
     * To start the HTTP server and apply configuration: port number, routes
     * @param promise callback to notify of HTTP server start | failure
     * @param vertx Entry point to Vert.X core, this is required to start the HTTP server
     * @param routes HTTP routes to apply (get, post, put, etc.)
     * @return HTTP server
     */
    private void startHttpServer(final Promise<Void> promise, final Vertx vertx, final Router routes) {
        // VertX starts a Netty server
        final HttpServer server = vertx.createHttpServer();

        // Configuration
        server
                // apply routes
                .requestHandler(routes)
                // bind server to port in async manner
                .listen(SERVER_PORT, asyncResult -> {
                    if (asyncResult.succeeded()) {
                        log.info("HTTP server running on port {}", server.actualPort());
                        promise.complete();
                    } else {
                        log.error("Could not start HTTP server.", asyncResult.cause());
                        promise.fail(asyncResult.cause());
                    }
                });
    }


    /**
     * To create and configure HTTP routes (endpoints)
     * @param vertx Entry point to Vert.X core, this is required to start the HTTP server
     * @return HTTP routes
     */
    private Router configureRoutes(final Vertx vertx) {
        final Router router = Router.router(vertx);

        // TODO add routes
        //      Routes have their own handlers, and they can be defined by URL and/or by HTTP method.
        //      It is a good idea to reference private methods for each route
        //      Note that URLs can be parametric: /wiki/:page will match a request like /wiki/Hello, in which case a "page" parameter will be available with value Hello

        // *** GETS
        // Application default's reply (path "/")
        router.route("/").handler(routingContext -> {
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

        return router;
    }
}
