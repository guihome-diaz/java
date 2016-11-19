package eu.daxiongmao.prv.server.dao.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import eu.daxiongmao.prv.server.config.OrikaConfiguration;
import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.model.business.User;
import eu.daxiongmao.prv.server.model.business.Vehicle;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(classes = { OrikaConfiguration.class })
public class UserMapperTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private OrikaBeanMapper mapper;

    @Test
    public void userMappingTest_dbToDto_noVehicles() {
        UserDB asterix = new UserDB("asterix@village-gaulois.fr", "test", "Asterix", "Le Gaulois");
        asterix.setId(1L);
        User item = mapper.map(asterix, User.class);
        Assert.assertEquals("asterix@village-gaulois.fr", item.getEmail());
        Assert.assertEquals("Asterix", item.getFirstName());
        Assert.assertEquals("Le Gaulois", item.getLastName());
        Assert.assertEquals((Long) 1L, item.getId());
        // Vehicles
        Assert.assertNotNull(item.getVehicles());
        Assert.assertEquals(0, item.getVehicles().size());
    }

    @Test
    public void userMappingTest_dbToDto_vehicles() {
        UserDB obelix = new UserDB("obelix@village-gaulois.fr", "test", "Obelix", "Le Gaulois");
        obelix.setId(1L);

        VehicleDB charAMenhirs = new VehicleDB(obelix, "uderzo", "menhir", "1", 100);
        VehicleDB charCompetion = new VehicleDB(obelix, "cesar", "rapido", "4 chevaux", 120);
        obelix.getVehicles().add(charAMenhirs);
        obelix.getVehicles().add(charCompetion);

        User item = mapper.map(obelix, User.class);

        Assert.assertEquals("obelix@village-gaulois.fr", item.getEmail());
        Assert.assertEquals("Obelix", item.getFirstName());
        Assert.assertEquals("Le Gaulois", item.getLastName());
        Assert.assertEquals((Long) 1L, item.getId());
        // Vehicles
        Assert.assertNotNull(item.getVehicles());
        Assert.assertEquals(2, item.getVehicles().size());
        Assert.assertEquals((Long) 1L, item.getVehicles().get(0).getUserId());

        LOGGER.debug("DB item: " + obelix);
        LOGGER.debug("Conversion: " + item);
    }

    @Test
    public void userMappingTest_dtoToDb_vehicles() {
        User obelix = new User("obelix@village-gaulois.fr", "Obelix", "Le Gaulois");
        obelix.setId(1000L);

        Vehicle charCompetition = new Vehicle();
        charCompetition.setBrand("romanus");
        charCompetition.setModel("cesar");
        charCompetition.setSerie("4 chevaux");
        charCompetition.setYearMake(120);
        charCompetition.setId(2L);
        // Specific
        charCompetition.setUserId(1000L);

        obelix.getVehicles().add(charCompetition);

        UserDB item = mapper.map(obelix, UserDB.class);

        Assert.assertEquals("obelix@village-gaulois.fr", item.getEmail());
        Assert.assertEquals("Obelix", item.getFirstName());
        Assert.assertEquals("Le Gaulois", item.getLastName());
        Assert.assertEquals((Long) 1000L, item.getId());
        // Vehicles
        Assert.assertNotNull(item.getVehicles());
        Assert.assertEquals(1, item.getVehicles().size());
        Assert.assertEquals((Long) 2L, item.getVehicles().get(0).getId());
        // **** UserDb to Long is UNIDIRECTIONAL == We expect NULL value ****
        Assert.assertNotNull(item.getVehicles().get(0).getUser());
        Assert.assertNull(item.getVehicles().get(0).getUser().getId());

        LOGGER.debug("DTO item: " + obelix);
        LOGGER.debug("Conversion: " + item);
    }

}
