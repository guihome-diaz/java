package eu.daxiongmao.prv.server.dao.mapper;

import java.time.LocalDate;

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
import eu.daxiongmao.prv.server.dao.model.RefuelDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.model.business.DrivingType;
import eu.daxiongmao.prv.server.model.business.Refuel;
import eu.daxiongmao.prv.server.util.DateTimeUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(classes = { OrikaConfiguration.class })
public class RefuelMapperTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(RefuelMapperTest.class);

    @Autowired
    private OrikaBeanMapper mapper;

    @Test
    public void refuelMappingTest_dbToDto_noVehicle() {
        RefuelDB refuel = new RefuelDB(null, LocalDate.of(2016, 1, 17), 32.05f, 487.2f, DrivingType.CITY, "driving test 1");
        refuel.setId(1L);

        Refuel item = mapper.map(refuel, Refuel.class);
        Assert.assertEquals(DateTimeUtils.asDate(LocalDate.of(2016, 1, 17)), item.getDate());
        Assert.assertEquals(32.05f, item.getVolume(), 0f);
        Assert.assertEquals(487.2f, item.getDistance(), 0f);
        Assert.assertEquals(DrivingType.CITY, item.getType());
        Assert.assertEquals((Long) 1L, item.getId());
        Assert.assertEquals("driving test 1", item.getComments());
        // Vehicle
        Assert.assertNull(item.getVehicleId());
    }

    @Test
    public void refuelMappingTest_dbToDto_vehicle() {
        VehicleDB charAMenhirs = new VehicleDB(null, "uderzo", "menhir-express", "2 chevaux", 100);
        charAMenhirs.setId(1000L);

        RefuelDB refuel = new RefuelDB(null, LocalDate.of(2016, 1, 17), 32.05f, 487.2f, DrivingType.HIGHWAY, "driving test 1");
        refuel.setId(1L);
        refuel.setVehicle(charAMenhirs);


        Refuel item = mapper.map(refuel, Refuel.class);
        Assert.assertEquals(DateTimeUtils.asDate(LocalDate.of(2016, 1, 17)), item.getDate());
        Assert.assertEquals(32.05f, item.getVolume(), 0f);
        Assert.assertEquals(487.2f, item.getDistance(), 0f);
        Assert.assertEquals(DrivingType.HIGHWAY, item.getType());
        Assert.assertEquals((Long) 1L, item.getId());
        Assert.assertEquals("driving test 1", item.getComments());
        // Vehicle
        Assert.assertNotNull(item.getVehicleId());
        Assert.assertEquals((Long) 1000L, item.getVehicleId());

        LOGGER.debug("DB item: " + refuel);
        LOGGER.debug("Conversion: " + item);
    }

    @Test
    public void refuelMappingTest_dtoToDb_noVehicle() {
        Refuel refuel = new Refuel();
        refuel.setComments("Another test");
        refuel.setDate(DateTimeUtils.asDate(LocalDate.of(2016, 11, 5)));
        refuel.setType(DrivingType.MIXED);
        refuel.setId(1L);
        refuel.setDistance(570.6f);
        refuel.setVolume(27.4f);

        RefuelDB item = mapper.map(refuel, RefuelDB.class);
        Assert.assertEquals(LocalDate.of(2016, 11, 5), item.getDate());
        Assert.assertEquals(27.4f, item.getVolume(), 0f);
        Assert.assertEquals(570.6f, item.getDistance(), 0f);
        Assert.assertEquals(DrivingType.MIXED, item.getType());
        Assert.assertEquals((Long) 1L, item.getId());
        Assert.assertEquals("Another test", item.getComments());
        // Vehicle
        Assert.assertNull(item.getVehicle());
    }

    @Test
    public void refuelMappingTest_dtoToDb_vehicle() {
        Refuel refuel = new Refuel();
        refuel.setComments("Another test");
        refuel.setDate(DateTimeUtils.asDate(LocalDate.of(2016, 11, 5)));
        refuel.setType(DrivingType.MIXED);
        refuel.setId(1L);
        refuel.setDistance(570.6f);
        refuel.setVolume(27.4f);
        // vehicle
        refuel.setVehicleId(1000L);

        RefuelDB item = mapper.map(refuel, RefuelDB.class);
        Assert.assertEquals(LocalDate.of(2016, 11, 5), item.getDate());
        Assert.assertEquals(27.4f, item.getVolume(), 0f);
        Assert.assertEquals(570.6f, item.getDistance(), 0f);
        Assert.assertEquals(DrivingType.MIXED, item.getType());
        Assert.assertEquals((Long) 1L, item.getId());
        Assert.assertEquals("Another test", item.getComments());
        // **** VehicleDb to Long is UNIDIRECTIONAL == We expect NULL value ****
        Assert.assertNotNull(item.getVehicle());
        Assert.assertNull(item.getVehicle().getId());
        Assert.assertNull(item.getVehicle().getBrand());

        LOGGER.debug("DB item: " + refuel);
        LOGGER.debug("Conversion: " + item);
    }
}
