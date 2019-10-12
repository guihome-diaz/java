package eu.daxiongmao.tracker.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TrackerApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name","tracker-api");
		SpringApplication.run(TrackerApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.properties("spring.config.name:tracker-api").sources(TrackerApiApplication.class);
	}
}
