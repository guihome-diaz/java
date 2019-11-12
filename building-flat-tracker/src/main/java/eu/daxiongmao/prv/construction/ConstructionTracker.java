package eu.daxiongmao.prv.construction;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ConstructionTracker extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {
        vertx.createHttpServer()
            // What to do
            .requestHandler(req -> {
                // TODO do something clever over here
                // Client response
                req.response()
                        .putHeader("Content-Type", "plain/text")
                        .end("Hello, welcome on the new application flat construction tracker");
                // Logging
                log.info("Handled a request on path {} from {}", req.path(), req.remoteAddress().host());
            })
            // Configuration
            .listen(11981, asyncResult -> {
                if (asyncResult.succeeded()) {
                    // send async success to client
                    promise.complete();
                } else {
                    // forward error
                    promise.fail(asyncResult.cause());
                }
            });
        log.info("Application startup complete! Ready to process requests");
    }

}
