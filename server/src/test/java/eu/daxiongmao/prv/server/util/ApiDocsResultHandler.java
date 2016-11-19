package eu.daxiongmao.prv.server.util;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.Validate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.google.common.base.Charsets;

public class ApiDocsResultHandler implements ResultHandler {

    public static class Builder {
        private final String outputDir;
        Builder(final String outputDir) {
            this.outputDir = outputDir;
        }

        public ApiDocsResultHandler build() {
            return new ApiDocsResultHandler(outputDir);
        }
    }

    private final String outputDir;

    ApiDocsResultHandler(final String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void handle(final MvcResult result) throws Exception {
        MockHttpServletResponse response = result.getResponse();
        String swaggerJson = response.getContentAsString();

        Files.createDirectories(Paths.get(outputDir));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), Charsets.UTF_8)){
            writer.write(swaggerJson);
        }
    }

    public static Builder outputDirectory(final String outputDir) {
        Validate.notEmpty(outputDir, "outputDir must not be empty!");
        return new Builder(outputDir);
    }

}
