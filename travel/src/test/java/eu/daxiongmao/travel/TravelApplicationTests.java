package eu.daxiongmao.travel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootTest(classes = {TravelApplication.class})
@PropertySources({
		@PropertySource("config/db-h2.properties")
})
class TravelApplicationTests {

	@Test
	void contextLoads() {
	}

}
