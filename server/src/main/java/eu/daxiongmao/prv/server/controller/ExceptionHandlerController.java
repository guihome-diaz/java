package eu.daxiongmao.prv.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import eu.daxiongmao.prv.server.model.exception.BusinessException;
import eu.daxiongmao.prv.server.model.exception.ErrorCodes;
import eu.daxiongmao.prv.server.model.exception.ExceptionDTO;
import eu.daxiongmao.prv.server.model.exception.RequestException;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ExceptionDTO handleBusinessException(final HttpServletRequest req, final BusinessException e) {
        return new ExceptionDTO(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestException.class)
    @ResponseBody
    public ExceptionDTO handleRequestException(final HttpServletRequest req, final RequestException e) {
        return new ExceptionDTO(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionDTO handleTechnicalError(final HttpServletRequest req, final Exception e, final HttpServletResponse res) {
        LOGGER.error("Technical failure", e);
        return new ExceptionDTO(ErrorCodes.TECHNICAL_ERROR, e.getMessage());
    }
}
