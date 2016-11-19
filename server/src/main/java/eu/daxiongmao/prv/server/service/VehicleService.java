package eu.daxiongmao.prv.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.daxiongmao.prv.server.dao.mapper.OrikaBeanMapper;
import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.dao.repository.UserRepository;
import eu.daxiongmao.prv.server.dao.repository.VehicleRepository;
import eu.daxiongmao.prv.server.model.business.Vehicle;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ErrorCodes;
import eu.daxiongmao.prv.server.model.exception.RequestException;

@Service
public class VehicleService {

    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);

    /** Default number of items on a a page. */
    public static final int DEFAULT_PAGE_SIZE = 50;

    @Autowired
    private OrikaBeanMapper mapper;

    @Autowired
    private VehicleRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Vehicle create(final Vehicle item) throws BusinessException {
        // Get user
        UserDB user = userRepository.findOne(item.getUserId());
        if (user == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_RELATED_ITEM_NOT_FOUND, "USER not found");
        }

        // Save values
        VehicleDB itemDb = mapper.map(item, VehicleDB.class);
        itemDb.setUser(user);
        itemDb = repository.save(itemDb);

        return mapper.map(itemDb, Vehicle.class);
    }

    public Vehicle update(final Long id, final Vehicle item) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }
        if (id != item.getId()) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "given ID in request path doesn't match entity's content");
        }

        // Ensure it exists
        VehicleDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        // Save new values
        // !! User management (add / deletion) is NOT managed over here!!
        VehicleDB itemDbUpdate = mapper.map(item, VehicleDB.class);
        itemDbUpdate.setUser(itemDb.getUser());
        itemDb = repository.save(itemDbUpdate);

        return mapper.map(itemDb, Vehicle.class);
    }

    public void delete(final Long id) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        // Ensure it exists
        VehicleDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        repository.delete(itemDb);
    }

    public Vehicle searchById(final Long id) throws RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        VehicleDB dbItem = repository.findOne(id);
        if (dbItem == null) {
            return null;
        } else {
            return mapper.map(dbItem, Vehicle.class);
        }
    }

    public List<Vehicle> findAllByUser(final Long userId) throws BusinessException {
        // Get vehicle
        UserDB user = userRepository.findOne(userId);
        if (user == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_RELATED_ITEM_NOT_FOUND, "USER not found");
        }
        // Get data
        List<VehicleDB> dbVehicles = repository.findAllByUser(user);
        // Cast data to DTO
        List<Vehicle> vehicles = new ArrayList<>();
        if (dbVehicles != null) {
            for (VehicleDB db : dbVehicles) {
                vehicles.add(mapper.map(db, Vehicle.class));
            }
        }
        return vehicles;
    }

}
