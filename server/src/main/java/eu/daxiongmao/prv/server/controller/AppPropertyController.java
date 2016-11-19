package eu.daxiongmao.prv.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.daxiongmao.prv.server.dao.model.AppProperty;
import eu.daxiongmao.prv.server.dao.repository.AppPropertyRepository;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ExceptionDTO;
import eu.daxiongmao.prv.server.model.exception.RequestException;
import eu.daxiongmao.prv.server.service.AppPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "properties")
@RestController
@RequestMapping(value = "/api/v1/properties", produces = "application/json")
public class AppPropertyController {

    @Autowired
    private AppPropertyRepository repository;
    @Autowired
    private AppPropertyService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "create",
            notes = "Create a new entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "createProperty")
    @ApiResponses({ @ApiResponse(code = 500, message = "Unkown exception", response = ExceptionDTO.class) })
    public AppProperty create(@RequestBody final AppProperty item) {
        return repository.save(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(
            value = "update",
            notes = "Update an entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "updateProperty")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public AppProperty update(
            @PathVariable(required = true, name="id") final Long id,
            @RequestParam(required = true, value = "key") final String key,
            @RequestParam(required = true, value = "value") final String value,
            @RequestParam(required = false, value = "description") final String description) throws BusinessException, RequestException {

        // (i) @RequestBody does NOT work with PUT

        return service.update(id, key, value, description);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(
            value = "delete",
            notes = "Delete the entity with the given id.",
            nickname = "deleteProperty")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public void delete(@PathVariable(required = true, name = "id") final Long id) throws BusinessException, RequestException {
        service.delete(id);
    }


    @RequestMapping(value = "/search/key/{key:.*}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchByKey",
            notes = "To search an item using a particular (unique) value[s]",
            nickname = "searchPropertyByKey")
    @ApiResponses({ @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class) })
    public AppProperty searchByKey(@PathVariable(required = true, name = "key") final String key) {
        // (i) @RequestBody does NOT work with GET
        return repository.findByKey(key);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchById",
            notes = "To retrieve an item from its ID.",
            nickname = "searchPropertyById")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class)  })
    public AppProperty searchById(@PathVariable(required = true, name = "id") final Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchAll",
            notes = "To retrieve all items",
            nickname = "searchAllProperties")
    @ApiResponses({ @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class) })
    public List<AppProperty> findAll() {
        return repository.findAll();
    }
}
