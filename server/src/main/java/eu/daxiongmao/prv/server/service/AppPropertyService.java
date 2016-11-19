package eu.daxiongmao.prv.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.model.AppProperty;
import eu.daxiongmao.prv.server.dao.repository.AppPropertyRepository;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ErrorCodes;
import eu.daxiongmao.prv.server.model.exception.RequestException;

@Component
public class AppPropertyService {

    @Autowired
    private AppPropertyRepository repository;

    public AppProperty update(final Long id, final String key, final String value, final String description) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        // Ensure it exists
        AppProperty itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        if (!itemDb.getKey().equals(key)) {
            // Ensure the key is not already used
            AppProperty otherProp = repository.findByKey(key);
            if (otherProp != null) {
                throw new BusinessException(ErrorCodes.TECHNICAL_ERROR_DUPLICATE_KEY, "This key is already used by another item. please update that item instead");
            }
        }

        // Update values
        itemDb.setKey(key);
        itemDb.setValue(value);
        if (description != null) {
            itemDb.setDescription(description);
        }
        return repository.save(itemDb);
    }

    public void delete(final Long id) throws BusinessException, RequestException {
        // param validation
        if (id == null || id < 0L) {
            throw new RequestException(ErrorCodes.REQUEST_ERROR_NO_ID, "ID is missing or invalid");
        }

        // Ensure it exists
        AppProperty itemDb = repository.findOne(id);
        if (itemDb == null) {
            throw new BusinessException(ErrorCodes.BUSINESS_ERROR_ITEM_NOT_FOUND, "Item with given ID does not exists");
        }

        repository.delete(itemDb);
    }

}
