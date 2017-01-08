package eu.daxiongmao.utils.eventbus;

/**
 * Callback interface of the event-bus.<br>
 * All event-bus listeners must implements that interface and method.<br>
 * You can process many events in a listener class: that is up to you!
 * 
 * @author Guillaume Diaz
 * @version 1.0 - 2017/01
 */
public interface EventListener {

    /**
     * Event notification.
     *
     * @param event
     *            event to process
     */
    void onEvent(Event event);

}
