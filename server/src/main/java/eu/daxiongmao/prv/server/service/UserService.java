package eu.daxiongmao.prv.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.mapper.OrikaBeanMapper;
import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.dao.repository.UserRepository;
import eu.daxiongmao.prv.server.model.business.User;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ErrorCodes;
import eu.daxiongmao.prv.server.model.exception.RequestException;

@Component
public class UserService {

    @Autowired
    private OrikaBeanMapper mapper;

    @Autowired
    private UserRepository repository;

    public User create(final User item) throws BusinessException {
        UserDB itemDb = mapper.map(item, UserDB.class);
        itemDb = repository.save(itemDb);
        return mapper.map(itemDb, User.class);
    }

    public User update(final Long id, final User item) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }
        if (id != item.getId()) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "given ID in request path doesn't match entity's content");
        }

        // Ensure it exists
        UserDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        // Save new values
        // !! Vehicle management (add / deletion) is NOT managed over here!!
        UserDB itemDbUpdate = mapper.map(item, UserDB.class);
        itemDbUpdate.setVehicles(itemDb.getVehicles());
        itemDb = repository.save(itemDbUpdate);

        return mapper.map(itemDb, User.class);
    }

    public void delete(final Long id) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        // Ensure it exists
        UserDB itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        repository.delete(itemDb);
    }

    public User searchById(final Long id) throws RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        UserDB dbItem = repository.findOne(id);
        if (dbItem == null) {
            return null;
        } else {
            return mapper.map(dbItem, User.class);
        }
    }

    public User searchByEmail(final String email) throws RequestException {
        // param validation
        if (email == null || email.trim().isEmpty()) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_INVALID_EMAIL, "Email is invalid");
        }

        UserDB dbItem = repository.findByEmail(email);
        if (dbItem == null) {
            return null;
        } else {
            return mapper.map(dbItem, User.class);
        }
    }

    public List<User> findAll() {
        List<UserDB> dbItems = repository.findAll();

        // Cast data to DTO
        List<User> items = new ArrayList<>();
        if (dbItems != null) {
            for (UserDB db : dbItems) {
                items.add(mapper.map(db, User.class));
            }
        }
        return items;
    }
}
