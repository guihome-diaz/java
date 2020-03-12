package eu.daxiongmao.travel.model.enums;


/**
 * List of business parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum BusinessParam {

    /** Default application language in case translation for requested language does not exists; or that user lang is not available (yet) */
    APP_DEFAULT_LANGUAGE("APP.DEFAULT_LANG", String.class),

    /** Application COMMERCIAL (public) version. This is not related to DB, Maven or GIT versions at all. */
    APP_VERSION("APP.VERSION", String.class);

    private String paramName;
    private Class clazz;

    BusinessParam(String paramName, Class clazz) {
        this.paramName = paramName;
        this.clazz = clazz;
    }

    /** @return parameter name as saved in DB */
    public String getParamName() {
        return paramName;
    }

    /**
     * @return parameter class (type)
     */
    public Class getClazz() {
        return clazz;
    }
}
