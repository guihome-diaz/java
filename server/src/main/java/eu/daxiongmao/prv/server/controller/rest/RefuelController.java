package eu.daxiongmao.prv.server.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.daxiongmao.prv.server.model.business.Refuel;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ExceptionDTO;
import eu.daxiongmao.prv.server.model.exception.RequestException;
import eu.daxiongmao.prv.server.service.RefuelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "refuels")
@RestController
@RequestMapping(value = "/api/v1/refuels", produces = "application/json")
public class RefuelController {

    @Autowired
    private RefuelService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "create",
            notes = "Create a new entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "createRefuel")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unkown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public Refuel create(@RequestBody final Refuel item) throws BusinessException {
        return service.create(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ApiOperation(
            value = "update",
            notes = "Update an entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "updateRefuel")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public Refuel update(@PathVariable(name = "id", required = true)  final Long id, @RequestBody final Refuel item) throws BusinessException, RequestException {
        return service.update(id, item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(
            value = "delete",
            notes = "Delete the entity with the given id.",
            nickname = "deleteRefuel")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public void delete(@PathVariable(required = true, name = "id") final Long id) throws BusinessException, RequestException {
        service.delete(id);
    }

    @RequestMapping(value = "/search/vehicle/{vehicleId}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchAllByVehicle",
            notes = "To retrieve all items",
            nickname = "searchAllRefuelByVehicle")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public List<Refuel> findAllByVehicle(@PathVariable(required = true, name = "vehicleId") final Long vehicleId) throws BusinessException{
        return service.findAllByVehicle(vehicleId);
    }
}
