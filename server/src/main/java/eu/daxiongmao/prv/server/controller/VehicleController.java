package eu.daxiongmao.prv.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.daxiongmao.prv.server.model.business.Vehicle;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ExceptionDTO;
import eu.daxiongmao.prv.server.model.exception.RequestException;
import eu.daxiongmao.prv.server.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "vehicles")
@RestController
@RequestMapping(value = "/api/v1/vehicles", produces = "application/json")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "create",
            notes = "Create a new entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "createVehicle")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unkown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public Vehicle create(@RequestBody final Vehicle item) throws BusinessException {
        return create(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ApiOperation(
            value = "update",
            notes = "Update an entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "updateVehicle")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public Vehicle update(@PathVariable(name = "id", required = true) final Long id, @RequestBody final Vehicle item) throws BusinessException, RequestException {
        return service.update(id, item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(
            value = "delete",
            notes = "Delete the entity with the given id.",
            nickname = "deleteVehicle")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public void delete(@PathVariable(name = "id", required = true)  final Long id) throws BusinessException, RequestException {
        service.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchById",
            notes = "To retrieve an item from its ID.",
            nickname = "searchVehicleById")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class) })
    public Vehicle searchById(@PathVariable(name = "id", required = true) final Long id) throws RequestException {
        return service.searchById(id);
    }

    @RequestMapping(value = "/search/user/{userId}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchAllByUser",
            notes = "To retrieve all items",
            nickname = "searchAllVehiclesByUser")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public List<Vehicle> findAllByUser(@PathVariable(required = true, name = "userId") final Long userId) throws BusinessException {
        return service.findAllByUser(userId);
    }
}
