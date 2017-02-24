package eu.daxiongmao.prv.server.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.daxiongmao.prv.server.model.business.User;
import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ExceptionDTO;
import eu.daxiongmao.prv.server.model.exception.RequestException;
import eu.daxiongmao.prv.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "users")
@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "create",
            notes = "Create a new entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "createUser")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unkown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public User create(@RequestBody final User item) throws BusinessException {
        return service.create(item);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ApiOperation(
            value = "update",
            notes = "Update an entity. Use the returned instance for further operations as the save operation might have changed the entity instance completely.",
            nickname = "updateUser")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public User update(@PathVariable(name = "id", required = true) final Long id, @RequestBody final User item) throws BusinessException, RequestException {
        return service.update(id, item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(
            value = "delete",
            notes = "Delete the entity with the given id.",
            nickname = "deleteUser")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class),
        @ApiResponse(code = 409, message = "Business error", response = ExceptionDTO.class) })
    public void delete(@PathVariable(name = "id", required = true) final Long id) throws BusinessException, RequestException {
        service.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchById",
            notes = "To retrieve an item from its ID.",
            nickname = "searchUserById")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class) })
    public User searchById(@PathVariable(name = "id", required = true) final Long id) throws RequestException {
        return service.searchById(id);
    }

    @RequestMapping(value = "/search/email/{email}", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchByEmail",
            notes = "To retrieve an item from its email.",
            nickname = "searchUserByEmail")
    @ApiResponses({
        @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class),
        @ApiResponse(code = 402, message = "Request error", response = ExceptionDTO.class)  })
    public User searchByEmail(@PathVariable(required = true, name = "email") final String email) throws RequestException {
        return service.searchByEmail(email);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(
            value = "searchAll",
            notes = "To retrieve all items",
            nickname = "searchAllUsers")
    @ApiResponses({ @ApiResponse(code = 500, message = "Unknown exception", response = ExceptionDTO.class) })
    public List<User> findAll() {
        return service.findAll();
    }
}
