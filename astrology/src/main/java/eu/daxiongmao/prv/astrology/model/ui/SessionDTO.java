package eu.daxiongmao.prv.astrology.model.ui;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

public class SessionDTO implements Serializable {

    private static final long serialVersionUID = -201701071730L;
    public static final Locale DEFAULT_LANG = new Locale("en", "GB");

    private static SessionDTO instance;

    /** Prevent instantiation of this class. You should only go through the Singleton pattern, using {@link #getInstance()}. */
    private SessionDTO() {
    }

    /** @return Singleton's object. */
    public static SessionDTO getInstance() {
        if (instance == null) {
            // Lazy Initialization (If required then only)
            // Thread Safe. Might be costly operation in some case
            synchronized (SessionDTO.class) {
                if (instance == null) {
                    instance = new SessionDTO();
                }
            }
        }
        return instance;
    }

    private Locale locale;

    private LocalDate birthDate;

    public Locale getLocale() {
        if (locale == null) {
            locale = DEFAULT_LANG;
        }
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
