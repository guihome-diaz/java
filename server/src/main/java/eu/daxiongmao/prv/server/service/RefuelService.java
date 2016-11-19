package eu.daxiongmao.prv.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.mapper.OrikaBeanMapper;
import eu.daxiongmao.prv.server.dao.model.RefuelDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.dao.repository.RefuelRepository;
import eu.daxiongmao.prv.server.dao.repository.VehicleRepository;
import eu.daxiongmao.prv.server.model.business.Refuel;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ErrorCodes;
import eu.daxiongmao.prv.server.model.exception.RequestException;

@Component
public class RefuelService {

    @Autowired
    private OrikaBeanMapper mapper;
    @Autowired
    private RefuelRepository repository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public Refuel create(final Refuel item) throws BusinessException {
        // Get vehicle
        VehicleDB veh = vehicleRepository.findOne(item.getVehicleId());
        if (veh == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_RELATED_ITEM_NOT_FOUND, "VEHICLE not found");
        }

        // Save values
        RefuelDB itemDb = mapper.map(item, RefuelDB.class);
        itemDb.setVehicle(veh);
        itemDb = repository.save(itemDb);

        return mapper.map(itemDb, Refuel.class);
    }

    public Refuel update(final Long id, final Refuel item) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }
        if (id != item.getId()) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "given ID in request path doesn't match entity's content");
        }

        // Ensure it exists
        RefuelDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        // Save new values
        // !! Vehicle management (add / deletion) is NOT managed over here!!
        RefuelDB itemDbUpdate = mapper.map(item, RefuelDB.class);
        itemDbUpdate.setVehicle(itemDb.getVehicle());
        itemDb = repository.save(itemDbUpdate);

        return mapper.map(itemDb, Refuel.class);
    }

    public void delete(final Long id) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        // Ensure it exists
        RefuelDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        repository.delete(itemDb);
    }

    public List<Refuel> findAllByVehicle(final Long vehicleId) throws BusinessException {
        // Get vehicle
        VehicleDB veh = vehicleRepository.findOne(vehicleId);
        if (veh == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_RELATED_ITEM_NOT_FOUND, "Vehicle not found");
        }
        // Get data
        List<RefuelDB> dbRefuels = repository.findAllByVehicleOrderByDateAsc(veh);
        // Cast data to DTO
        List<Refuel> refuels = new ArrayList<>();
        if (dbRefuels != null) {
            for (RefuelDB db : dbRefuels) {
                refuels.add(mapper.map(db, Refuel.class));
            }
        }
        return refuels;
    }
}
