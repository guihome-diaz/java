package eu.daxiongmao.prv.server.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;

@Transactional
public interface VehicleRepository extends JpaRepository<VehicleDB, Long> {

    VehicleDB findByBrandAndModel(String brand, String model);

    List<VehicleDB> findAllByUser(UserDB user);

}
