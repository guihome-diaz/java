package eu.daxiongmao.travel.dao;

import eu.daxiongmao.travel.TravelApplication;
import eu.daxiongmao.travel.model.db.Parameter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TravelApplication.class})
@PropertySources({
        //@PropertySource("config/db-h2.properties")
        @PropertySource("config/db-oracle.properties")
})
@Log4j2
public class ParameterRepositoryTest {

    @Autowired
    private ParameterRepository parameterRepository;

    @Test
    public void findAll() {
        Assertions.assertNotNull(parameterRepository);
        final List<Parameter> parameters = parameterRepository.findAll();
        Assertions.assertNotNull(parameters);
        Assertions.assertFalse(parameters.isEmpty());
        parameters.forEach((parameter -> {log.info(parameter); }));
    }

}
