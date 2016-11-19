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
import eu.daxiongmao.prv.server.model.business.Vehicle;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(classes = { OrikaConfiguration.class })
public class VehicleMapperTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleMapperTest.class);

    @Autowired
    private OrikaBeanMapper mapper;

    @Test
    public void vehicleMappingTest_dbToDto_noUser() {
        VehicleDB charAMenhirs = new VehicleDB(null, "uderzo", "menhir-express", "2 chevaux", 100);
        charAMenhirs.setId(1L);

        Vehicle item = mapper.map(charAMenhirs, Vehicle.class);
        Assert.assertNotNull(item);
        Assert.assertEquals("uderzo", item.getBrand());
        Assert.assertEquals("menhir-express", item.getModel());
        Assert.assertEquals("2 chevaux", item.getSerie());
        Assert.assertEquals((Integer) 100, item.getYearMake());
        Assert.assertEquals((Long) 1L, item.getId());
    }

    @Test
    public void vehicleMappingTest_dbToDTO_user() {
        UserDB obelix = new UserDB("obelix@village-gaulois.fr", "test", "Obelix", "Le Gaulois");
        obelix.setId(1000L);

        VehicleDB charAMenhirs = new VehicleDB(obelix, "uderzo", "menhir-express", "2 chevaux", 100);
        charAMenhirs.setId(1L);
        charAMenhirs.setUser(obelix);

        Vehicle item = mapper.map(charAMenhirs, Vehicle.class);
        Assert.assertNotNull(item);
        Assert.assertEquals("uderzo", item.getBrand());
        Assert.assertEquals("menhir-express", item.getModel());
        Assert.assertEquals("2 chevaux", item.getSerie());
        Assert.assertEquals((Integer) 100, item.getYearMake());
        Assert.assertEquals((Long) 1L, item.getId());
        Assert.assertEquals((Long) 1000L, item.getUserId());

        LOGGER.debug("DB item: " + charAMenhirs);
        LOGGER.debug("Conversion: " + item);
    }

    @Test
    public void vehicleMappingTest_dtoToDb_noUser() {
        Vehicle charCompetition = new Vehicle();
        charCompetition.setBrand("romanus");
        charCompetition.setModel("cesar");
        charCompetition.setSerie("4 chevaux");
        charCompetition.setYearMake(120);
        charCompetition.setId(2L);

        VehicleDB item = mapper.map(charCompetition, VehicleDB.class);
        Assert.assertNotNull(item);
        Assert.assertEquals("romanus", item.getBrand());
        Assert.assertEquals("cesar", item.getModel());
        Assert.assertEquals("4 chevaux", item.getSerie());
        Assert.assertEquals((Integer) 120, item.getYearMake());
        Assert.assertEquals((Long) 2L, item.getId());
        Assert.assertNull(item.getUser());
    }

    @Test
    public void vehicleMappingTest_dtoToDb_user() {
        Vehicle charCompetition = new Vehicle();
        charCompetition.setBrand("romanus");
        charCompetition.setModel("cesar");
        charCompetition.setSerie("4 chevaux");
        charCompetition.setYearMake(120);
        charCompetition.setId(2L);
        // Specific
        charCompetition.setUserId(1000L);

        VehicleDB item = mapper.map(charCompetition, VehicleDB.class);
        Assert.assertNotNull(item);
        Assert.assertEquals("romanus", item.getBrand());
        Assert.assertEquals("cesar", item.getModel());
        Assert.assertEquals("4 chevaux", item.getSerie());
        Assert.assertEquals((Integer) 120, item.getYearMake());
        Assert.assertEquals((Long) 2L, item.getId());
        Assert.assertNotNull(item.getUser());
        // **** UserDb to Long is UNIDIRECTIONAL == We expect NULL value ****
        // Assert.assertEquals((Long) 1000L, item.getUser().getId());
        Assert.assertNull(item.getUser().getId());
        Assert.assertNull(item.getUser().getEmail());
        Assert.assertNull(item.getUser().getFirstName());
        Assert.assertNull(item.getUser().getLastName());

        LOGGER.debug("DTO item: " + charCompetition);
        LOGGER.debug("Conversion: " + item);
    }
}
