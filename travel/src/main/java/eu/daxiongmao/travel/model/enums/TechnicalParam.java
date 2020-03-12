package eu.daxiongmao.travel.model.enums;

/**
 * List of technical parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum TechnicalParam {

    /** Current mode of the application. To get the allowed values see the corresponding Java ENUM in the code */
    MODE("APP.MODE", String.class),

    /** Environment specifics values */
    ENVIRONMENT("APP.ENVIRONMENT", String.class),

    /** Parameters caching refresh */
    MIN_TIME_IN_SECONDS_BETWEEN_CACHE_REFRESH("APP.CACHE.REFRESH.MINIMUM_DELAY_BETWEEN_REFRESH_IN_SECONDS", Integer.class),

    /** DB schema version */
    DATABASE_VERSION("APP.DB.VERSION", String.class);

    private String paramName;
    private Class clazz;

    TechnicalParam(String paramName, Class clazz) {
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
