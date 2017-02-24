package eu.daxiongmao.prv.server.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.io.IOException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicate;

import eu.daxiongmao.prv.server.ServletInitializer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {

    // All the following is based on the excellent tutorial: http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

    @Bean
    public Docket getApiDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.any()) // Comment ALL REST web-services
                .paths(paths()) // Comment only services available on /api/*
                .build();
    }

    private Predicate<String> paths() {
        return regex("/api/.*");
    }

    private ApiInfo getApiInfo() {
        final Contact apiOwner = new Contact("Guillaume Diaz", "http://daxiongmao.eu/wiki", "guillaume@qin-diaz.com");
        final ApiInfo apiInfo = new ApiInfo("REST documentation", "REST web-service APIs description", getVersion(), "Terms of services", apiOwner,
                "Copyright 2016 - Daxiongmao.eu # Apache 2.0", "http://choosealicense.com/licenses/apache-2.0/");
        return apiInfo;
    }

    /** Retrieve version from Manifest.mf */
    private String getVersion() {
        try {
            if (ServletInitializer.manifest == null) {
                throw new IOException();
            }

            final String version = ServletInitializer.manifest.getValue("Implementation-Version");
            final String build = ServletInitializer.manifest.getValue("Build-Number");
            final String revision = ServletInitializer.manifest.getValue("Svn-Revision");
            if (version == null) {
                throw new IOException();
            }

            return String.format("%s (b%s, r%s)", version, build, revision);
        } catch (final IOException e) {
            return "NA";
        }
    }

}
