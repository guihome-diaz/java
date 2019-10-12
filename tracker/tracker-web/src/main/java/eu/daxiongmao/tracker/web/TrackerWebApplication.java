package eu.daxiongmao.tracker.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TrackerWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name","tracker-web");
		SpringApplication.run(TrackerWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.properties("spring.config.name:tracker-web").sources(TrackerWebApplication.class);
	}
}
