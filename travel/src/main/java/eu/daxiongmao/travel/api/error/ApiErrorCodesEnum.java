package eu.daxiongmao.travel.api.error;

/**
 * List of all errors codes
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/02
 */
public enum ApiErrorCodesEnum {

    /** Default error */
    UNEXPECTED_ERROR("Unexpected error"),

    BAD_REQUEST_MALFORMED("Malformed JSON request"),

    FORBIDDEN("You are not allowed to perform the request operation / access to that particular data. Please contact our helpdesk");

    /** Default error message to return in plain English */
    private String defaultErrorMessage;

    /**
     * To declare a new error code with default error message
     * @param defaultErrorMessage default error message to display, in plain English
     */
    ApiErrorCodesEnum(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }

    /**
     * @return Default error message to return in plain English
     */
    public String getDefaultErrorMessage() {
        return defaultErrorMessage;
    }
}
