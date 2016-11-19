package eu.daxiongmao.prv.server;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    public static Attributes manifest;

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        try {
            manifest = new Manifest(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")).getMainAttributes();
        } catch (IOException e) {
            // Nothing
        }
        super.onStartup(servletContext);
    }


}
