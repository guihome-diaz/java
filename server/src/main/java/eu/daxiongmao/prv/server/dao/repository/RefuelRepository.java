package eu.daxiongmao.prv.server.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.daxiongmao.prv.server.dao.model.RefuelDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;

@Transactional
public interface RefuelRepository extends JpaRepository<RefuelDB, Long> {

    List<RefuelDB> findAllByVehicleOrderByDateAsc(VehicleDB veh);
}
