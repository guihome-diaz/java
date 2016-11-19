package eu.daxiongmao.prv.server.model.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Exception DTO returned by the REST services.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "urn:eu.daxiongmao", propOrder = { "code", "message" })
@XmlRootElement(name = "exception")
public class ExceptionDTO implements Serializable {

    private static final long serialVersionUID = -20161103L;

    @XmlElement(required = true, name = "code")
    private String code;

    @XmlElement(required = true, name = "message")
    private String message;

    public ExceptionDTO(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
