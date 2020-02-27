package eu.daxiongmao.travel.api;

import eu.daxiongmao.travel.api.error.ApiError;
import eu.daxiongmao.travel.api.error.ApiErrorCodesEnum;
import eu.daxiongmao.travel.model.exception.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller that acts like an aspect to handle all exceptions and convert them into HTTP correct response.
 * @author Guillaume Diaz (implementation)
 * @author Spring boot team / community (design)
 * @version 1.0
 * @since 2020/02
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    /**
     * Override Spring default error.<br>
     *     Return BAD_REQUEST in case of malformed / corrupted message
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ApiErrorCodesEnum.BAD_REQUEST_MALFORMED, ex));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleNotAuthorized(Exception ex) throws Exception {
        if (ex instanceof UnauthorizedException) {
            return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, ApiErrorCodesEnum.FORBIDDEN, ex));
        }

        // Unknown exception, typically a wrapper.
        // We only deal with top-level exceptions here, so let's rethrow the given exception for further processing
        // through the HandlerExceptionResolver chain.
        throw ex;
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
