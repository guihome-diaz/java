package eu.daxiongmao.prv.server;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.model.AppProperty;
import eu.daxiongmao.prv.server.dao.model.RefuelDB;
import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.dao.repository.AppPropertyRepository;
import eu.daxiongmao.prv.server.dao.repository.RefuelRepository;
import eu.daxiongmao.prv.server.dao.repository.UserRepository;
import eu.daxiongmao.prv.server.dao.repository.VehicleRepository;
import eu.daxiongmao.prv.server.model.business.DrivingType;

@Component
public class DefaultDataset {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataset.class);

    @Autowired
    private AppPropertyRepository appPropertyRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RefuelRepository refuelRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initDataset() {

        // ****** CLEAN DB ******
        LOGGER.info("Cleaning DB");
        appPropertyRepository.deleteAllInBatch();
        refuelRepository.deleteAllInBatch();
        vehicleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        // ****** APPLICATION PROPERTIES ******
        LOGGER.info("Setup application properties");
        appPropertyRepository.save(new AppProperty("db.version", "1", "Database version"));
        appPropertyRepository.save(new AppProperty("app.version", "1.0", "Application version"));
        appPropertyRepository.save(new AppProperty("copyright", "Daxiongmao.eu 2016", null));

        // ****** USERS *******
        LOGGER.info("Add users...");
        UserDB admin = userRepository.save(new UserDB("admin@admin.eu", "test", "Administrator", ""));
        UserDB asterix = userRepository.save(new UserDB("asterix@village-gaulois.fr", "test", "Asterix", "Le Gaulois"));


        // ****** VEHICLE *******
        LOGGER.info("Add vehicles...");
        vehicleRepository.save(new VehicleDB(admin, "Nissan", "Note", "Tekna", 2016));
        vehicleRepository.save(new VehicleDB(admin, "Peugeot", "106", "kid", 1996));
        vehicleRepository.save(new VehicleDB(admin, "Peugeot", "309", "GLD", 1994));
        vehicleRepository.save(new VehicleDB(asterix, "Citroen", "C1", "-", 2009));

        // ****** REFUELS *******
        LOGGER.info("Add Refuels...");
        // Retrieve vehicle and add refuels
        final VehicleDB nissanNote = vehicleRepository.findByBrandAndModel("Nissan", "Note");
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 5, 1), 25.11f, 487.1f, DrivingType.MIXED, "61 km au compteur a reception du vehicule"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 5, 14), 27.5f, 374.3f, DrivingType.CITY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 6, 11), 20.04f, 261.8f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 6, 12), 32.08f, 506.6f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 6, 25), 19f, 314.1f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 21), 33.75f, 517.8f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 22), 34f, 550.7f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 23), 20f, 317.1f, DrivingType.HIGHWAY, "4 adults"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 24), 22.71f, 418.6f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 24), 26.15f, 360.8f, DrivingType.HIGHWAY, null));

        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 7, 31), 13.5f, 189.6f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 7), 32f, 504.6f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 15), 34.12f, 548.1f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 18), 52.19f, 870.7f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 22), 22f, 347f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 24), 29.6f, 570f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 26), 26f, 419.5f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 28), 33.15f, 461.7f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 8, 28), 34f, 547.3f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 9, 4), 19.77f, 368f, DrivingType.HIGHWAY, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 9, 10), 32f, 407f, DrivingType.HIGHWAY, null));

        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 9, 17), 23.68f, 417.9f, DrivingType.HIGHWAY, "2 adults"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 10, 9), 30.05f, 466.5f, DrivingType.MIXED, "Switch to winter tires"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 10, 22), 24.21f, 362.9f, DrivingType.MIXED, null));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 10, 29), 26.23f, 378.7f, DrivingType.HIGHWAY, "Trip to Munchen"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 10, 29), 18.01f, 281.1f, DrivingType.HIGHWAY, "Trip to Munchen"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 11, 02), 20.70f, 280f, DrivingType.HIGHWAY, "Back to Luxembourg"));
        refuelRepository.save(new RefuelDB(nissanNote, LocalDate.of(2016, 11, 02), 18.75f, 325f, DrivingType.HIGHWAY, "Back to Luxembourg"));
    }

}
