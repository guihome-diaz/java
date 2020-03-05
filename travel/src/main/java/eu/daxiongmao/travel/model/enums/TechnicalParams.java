package eu.daxiongmao.travel.model.enums;

/**
 * List of technical parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum TechnicalParams {

    /** Environment specifics values */
    ENVIRONMENT("environment", String.class),

    /** Parameters caching refresh */
    MIN_TIME_IN_SECONDS_BETWEEN_CACHE_REFRESH("cache.refresh.minimumDelayBetweenRefreshInSeconds", Integer.class),

    /** DB schema version */
    DATABASE_VERSION("db.version", String.class);

    private String paramName;
    private Class clazz;

    TechnicalParams(String paramName, Class clazz) {
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
