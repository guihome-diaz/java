package eu.daxiongmao.travel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {TravelApplication.class})
@ActiveProfiles({"test"})
public class TravelApplicationTests {

	@Test
	void contextLoads() {
	}

}
