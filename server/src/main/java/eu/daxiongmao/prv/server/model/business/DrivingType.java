package eu.daxiongmao.prv.server.model.business;

/**
 * List of available driving types.
 *
 * @author Guillaume Diaz
 */
public enum DrivingType {

    /** City and urban area driving. */
    CITY,

    /** Motorway driving all the way. */
    HIGHWAY,

    /** Both motorway & city driving |or| rural area. */
    MIXED;

}
