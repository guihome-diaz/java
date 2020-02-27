package eu.daxiongmao.travel.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * To encapsulate the error into a dedicated "error" JSON object that is better that the default SpringBoot error response.
 * @author Guillaume Diaz (implementation)
 * @version 1.0
 * @since 2020/02
 */
@Getter
@Setter
@ToString(of = { "httpStatus", "errorCode", "errorMessage", "timestamp" })
@EqualsAndHashCode(of = {"httpStatus", "errorCode"})
@JsonRootName("error")
@AllArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 20200227L;

    /** HTTP status of the error (4xx = client errors | 5xx = server errors) */
    private HttpStatus httpStatus;

    /** Date-time of the error in human format */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", locale = "fr_FR")
    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("Europe/Paris"));

    /** Error code to use for UI rendering and translation */
    private String errorCode;

    /** Error message: user-friendly description of the error in English */
    private String errorMessage;

    /** Complete error message, only useful for developers */
    private String debugMessage;

    /**
     * <strong>To represent multiple errors in a single call</strong>: property that holds an array of sub-errors that happened.<br>
     * An example would be validation errors in which multiple fields have failed the validation.
     */
    private List<ApiValidationError> validationErrors;

    /**
     * New error (UNEXPECTED ERROR)
     * @param httpStatus HTTP status of the error (4xx = client errors | 5xx = server errors)
     */
    public ApiError(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorMessage = ApiErrorCodesEnum.UNEXPECTED_ERROR.getDefaultErrorMessage();
        this.errorCode = ApiErrorCodesEnum.UNEXPECTED_ERROR.name();
    }

    /**
     * New error
     * @param httpStatus HTTP status of the error (4xx = client errors | 5xx = server errors)
     * @param error error to return to the user. <br>* error code = Enum name<br>* error description = Enum text
     */
    public ApiError(HttpStatus httpStatus, ApiErrorCodesEnum error) {
        this(httpStatus);
        if (error != null) {
            this.errorMessage = error.getDefaultErrorMessage();
            this.errorCode = error.name();
        }
    }

    /**
     * New error
     * @param httpStatus HTTP status of the error (4xx = client errors | 5xx = server errors)
     * @param error error to return to the user. <br>* error code = Enum name<br>* error description = Enum text
     * @param errorMessage custom error message
     */
    public ApiError(HttpStatus httpStatus, ApiErrorCodesEnum error, String errorMessage) {
        this(httpStatus, error);
        if (StringUtils.isNotBlank(errorMessage)) {
            this.errorMessage = errorMessage;
        }
    }

    /**
     * New error
     * @param httpStatus HTTP status of the error (4xx = client errors | 5xx = server errors)
     * @param error error to return to the user. <br>* error code = Enum name<br>* error description = Enum text
     * @param e stacktrace to return as debug log. This will only be used if provided and allowed by configuration
     */
    public ApiError(HttpStatus httpStatus, ApiErrorCodesEnum error, Throwable e) {
        this(httpStatus, error);

        // TODO add check to send stacktrace only if there parameter "web.display-stacktraces" is TRUE
        if (e != null) {
            this.debugMessage = ExceptionUtils.getStackTrace(e);
        }
    }

    /**
     * New error
     * @param httpStatus HTTP status of the error (4xx = client errors | 5xx = server errors)
     * @param error error to return to the user. <br>* error code = Enum name<br>* error description = Enum text
     * @param errorMessage custom error message
     * @param e stacktrace to return as debug log. This will only be used if provided and allowed by configuration
     */
    public ApiError(HttpStatus httpStatus, ApiErrorCodesEnum error, String errorMessage, Throwable e) {
        this(httpStatus, error, errorMessage);

        // TODO add check to send stacktrace only if there parameter "web.display-stacktraces" is TRUE
        if (e != null) {
            this.debugMessage = ExceptionUtils.getStackTrace(e);
        }
    }

    /**
     * To add an error cause.<br>
     *     Use that to return multiple errors at once, particularly useful for validation reports
     * @param validationError error cause to add
     */
    public void addValidationError(ApiValidationError validationError) {
        if (validationError == null) {
            return;
        }

        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(validationError);
    }
}
