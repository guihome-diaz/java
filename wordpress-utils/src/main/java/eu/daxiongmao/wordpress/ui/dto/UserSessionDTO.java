package eu.daxiongmao.wordpress.ui.dto;

import java.io.Serializable;
import java.util.Locale;

/**
 * Representation of the user session.
 * 
 * @author Guillaume Diaz
 * @version 1.0 - June 2017
 */
public class UserSessionDTO implements Serializable {

    private static final long serialVersionUID = -20170602L;

    private Locale locale = new Locale("en", "GB");

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }
}
