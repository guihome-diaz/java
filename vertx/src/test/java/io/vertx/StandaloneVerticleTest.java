package io.vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.assertj.core.api.Assertions;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The following Verticle tests are based on the official VertX jUnit5 examples,
 * see https://github.com/vert-x3/vertx-examples/blob/master/junit5-examples
 * @author Guillaume Diaz
 */
// How tests suit will appear in Maven and IDE
@DisplayName("Standalone vertx tests")
// Load VertX testing tools
@ExtendWith(VertxExtension.class)
public class StandaloneVerticleTest {
	
    @Test
    @DisplayName("⏱ Count 3 timer ticks")
    public void countThreeTicks(Vertx vertx, VertxTestContext testContext) {
        AtomicInteger counter = new AtomicInteger();
        vertx.setPeriodic(100, id -> {
            if (counter.incrementAndGet() == 3) {
                testContext.completeNow();
            }
        });
    }
}
