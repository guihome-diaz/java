package eu.daxiongmao.prv.server.model.exception;

/**
 * Functional error exception.<br>
 * This should be catch by a global exception handler and returns HTTP 409 (CONFLICT)
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -20161103L;

    private String code;

    public BusinessException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(final String code, final String message, final Throwable t) {
        super(message, t);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("Error code: %s%n", code));
        buffer.append(super.toString());
        return buffer.toString();
    }
}
