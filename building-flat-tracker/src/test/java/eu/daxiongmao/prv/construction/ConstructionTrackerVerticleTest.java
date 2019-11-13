package eu.daxiongmao.prv.construction;

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
@DisplayName("Construction verticle tests - application main entry point")
// Load VertX testing tools
@ExtendWith(VertxExtension.class)
class ConstructionTrackerVerticleTest {


    @Test
    @DisplayName("⏱ Count 3 timer ticks")
    void countThreeTicks(Vertx vertx, VertxTestContext testContext) {
        AtomicInteger counter = new AtomicInteger();
        vertx.setPeriodic(100, id -> {
            if (counter.incrementAndGet() == 3) {
                testContext.completeNow();
            }
        });
    }

    /**
     * Test that starts the VertX server (on netty) and perform queries on it
     * @param vertx vertx testing
     * @param testContext vertx context
     */
    @Test
    @DisplayName("Deploy verticle as HTTP service and make 10 requests")
    void useConstructionTrackerVerticle(Vertx vertx, VertxTestContext testContext) {
        // Init vertX testing tool
        WebClient webClient = WebClient.create(vertx);

        // *** Configure test case: set milestones to reach to trigger test completion
        Checkpoint deploymentCheckpoint = testContext.checkpoint();
        Checkpoint requestCheckpoint = testContext.checkpoint(10);

        // *** Start VertX verticle
        vertx.deployVerticle(new ConstructionTrackerVerticle(), testContext.succeeding(id -> {
            // Mark deployment as OK
            deploymentCheckpoint.flag();

            for (int i = 0; i < 10; i++) {
                // *** Query verticle
                webClient.get(11981, "localhost", "/")
                        .as(BodyCodec.string())
                        .send(testContext.succeeding(resp -> {
                            testContext.verify(() -> {
                                Assertions.assertThat(resp.statusCode()).isEqualTo(200);
                                Assertions.assertThat(resp.body()).contains("Hello");
                                // Flag every query
                                requestCheckpoint.flag();
                            });
                        }));
            }
        }));
    }
}
