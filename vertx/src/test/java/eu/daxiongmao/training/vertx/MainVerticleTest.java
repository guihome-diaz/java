package eu.daxiongmao.training.vertx;

import eu.daxiongmao.training.vertx.wiki.MainVerticle;
import eu.daxiongmao.training.vertx.wiki.config.HttpServerConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The following Verticle tests are based on the official VertX jUnit5 examples,
 * see https://github.com/vert-x3/vertx-examples/blob/master/junit5-examples
 * @author Guillaume Diaz
 */
// How tests suit will appear in Maven and IDE
@DisplayName("Main verticle tests")
// Load VertX testing tools
@ExtendWith(VertxExtension.class)
public class MainVerticleTest {


    /**
     * Test that starts the VertX server (on netty) and perform queries on it
     * @param vertx vertx testing
     * @param testContext vertx context
     */
    @Test
    @DisplayName("Deploy verticle as HTTP service and make 10 requests")
    public void testStartMainVerticle(Vertx vertx, VertxTestContext testContext) {
        // Init vertX testing tool
        WebClient webClient = WebClient.create(vertx);

        // *** Configure test case: set milestones to reach to trigger test completion
        Checkpoint deploymentCheckpoint = testContext.checkpoint();
        Checkpoint requestCheckpoint = testContext.checkpoint(10);

        // *** Start VertX verticle
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> {
            // Mark deployment as OK
            deploymentCheckpoint.flag();

            for (int i = 0; i < 10; i++) {
                // *** Query verticle
                webClient.get(HttpServerConfiguration.SERVER_PORT, "localhost", "/")
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
