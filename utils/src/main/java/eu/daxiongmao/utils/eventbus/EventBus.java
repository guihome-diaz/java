package eu.daxiongmao.utils.eventbus;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage events. <br>
 * This allows publish / subscription to event(s).<br>
 * This is a quick and basic implementation of the Event Bus pattern.<br>
 * This event bus uses {@link Executors#newCachedThreadPool()}: as a result you should NOT call the eventBus a thousand times per seconds.<br>
 * </p>
 *
 * <h2>Key points</h2>
 * <ul>
 * <li>Listeners must register themselves to a particular event(s).</li>
 * <li>1 listener can subscribe to many events ; however the listener must call the <code>subscribe</code> method for each event it wants to listen</li>
 * <li>Producers don't need to register. They can directly call the <code>publish</code> method</li>
 * <li>You can choose between different ways of working, see {@link EventBusMode}.<br>
 * By default the event bus uses {@link EventBusMode#CONSUMER_THREAD}.<br>
 * (i) You can change this behavior through the <code>configuration</code> method</li>
 * </ul>
 *
 *
 * <h2>Usage</h2>
 *
 *
 * </p>
 * <p>
 * If you want to use a more advanced event-bus then you can use the <a href="https://github.com/google/guava/wiki/EventBusExplained"><i>Google's Guava Event Bus</i></a><br>
 * (i) JavaFX's author [Greg Brown] recommend to use the event-bus pattern to share data between screens.
 * </p>
 *
 * @author Guillaume Diaz
 * @version 1.0 - 2017/01
 */
public class EventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    /** Event bus behavior. See {@link EventBusMode}. */
    private static EventBusMode behavior = EventBusMode.CONSUMER_THREAD;

    /** The thread cached pool will create as many threads as need on request and do as much re-use as possible. */
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    /** Private factory design pattern. Prevent instantiation of utility classes. */
    private EventBus() {
    }

    /**
     * Dictionary of events listeners subscription.
     * <ul>
     * <li>Key = event name</li>
     * <li>Value = list of subscribers' IDs. This ID is returned to the listener on subscription ; it can be used to un-subscribe to a particular event.</li>
     * </ul>
     */
    private static final Map<String, Set<String>> SUBSCRIBER_IDS = new ConcurrentHashMap<>();

    /**
     * Set of listeners.
     * <ul>
     * <li>Key = event subscriber ID</li>
     * <li>Value = corresponding listener</li>
     * </ul>
     */
    private static final Map<String, EventListener> LISTENERS = new ConcurrentHashMap<>();

    /**
     * To subscribe to a new event.
     *
     * @param eventName
     *            event name to listen to
     * @param listener
     *            listener instance
     * @return subscription ID. Listener should keep that subscriber ID, you can re-use it later on to un-subscribe to that event.
     */
    public static String subscribeToEvent(final String eventName, final EventListener listener) {
        // Generate unique ID for the new subscriber
        final String listenerUUID = UUID.randomUUID().toString();

        // Register the new subscriber
        if (SUBSCRIBER_IDS.containsKey(eventName)) {
            SUBSCRIBER_IDS.get(eventName).add(listenerUUID);
        } else {
            final Set<String> eventSubscribers = new HashSet<>();
            eventSubscribers.add(listenerUUID);
            SUBSCRIBER_IDS.put(eventName, eventSubscribers);
        }

        // Add binding
        LISTENERS.put(listenerUUID, listener);

        LOGGER.info(String.format("Add new listener for event: %s | listener UUID: %s", eventName, listenerUUID));
        return listenerUUID;
    }

    /**
     * To un-subscribe to a particular event.
     *
     * @param eventSubscriptionId
     *            event subscription ID
     * @return boolean flag. "true" if the subscription has been successfully removed ; 'false' otherwise [i.e subscriber not found].
     */
    public static boolean unsubscribeToEvent(final String eventSubscriptionId) {
        boolean hasBeenRemoved = false;
        if (LISTENERS.containsKey(eventSubscriptionId)) {
            for (final Entry<String, Set<String>> eventSubscribers : SUBSCRIBER_IDS.entrySet()) {
                if (eventSubscribers.getValue().remove(eventSubscriptionId)) {
                    LOGGER.info(String.format("Removing listener for event: %s | listener UUID: %s", eventSubscribers.getKey(), eventSubscriptionId));
                    hasBeenRemoved = true;
                }
            }
            LISTENERS.remove(eventSubscriptionId);
        }
        return hasBeenRemoved;
    }

    /**
     * To publish a new event.
     *
     * @param newEvent
     *            event to publish. This must not be NULL and it must have a valid name.
     */
    public static void publishEvent(final Event newEvent) {
        // Validation
        if (newEvent == null || newEvent.getEventName() == null || newEvent.getEventName().isEmpty()) {
            throw new IllegalArgumentException("You must provide a valid EVENT. It must not be NULL and it must have a valid name");
        }

        // Business
        if (SUBSCRIBER_IDS.containsKey(newEvent.getEventName()) == false) {
            LOGGER.warn(String.format("No listeners are available to process the given event type: %s%nEvent description:%n%s", newEvent.getEventName(), newEvent));
            return;
        }

        // Process event according to current configuration
        switch (behavior) {
        case NO_THREADS:
            publishEventNoThreads(newEvent);
            break;
        case EVENT_THREAD:
            publishEventSingleThread(newEvent);
            break;
        case CONSUMER_THREAD:
            publishEventMultiThreads(newEvent);
            break;
        default:
            LOGGER.error("Unknown mode: " + behavior + " ... Switching to default " + EventBusMode.NO_THREADS.name() + " name");
            publishEventNoThreads(newEvent);
            break;
        }

    }

    private static void publishEventNoThreads(final Event newEvent) {
        for (final String subscriber : SUBSCRIBER_IDS.get(newEvent.getEventName())) {
            LOGGER.debug(String.format("New event notification. Event name: %s | Event subscriber ID: %s", newEvent.getEventName(), subscriber));
            Thread.currentThread().setName(String.format("Event: %s | Listener: %s | Mode: %s", newEvent.getEventName(), subscriber, behavior.name()));
            LISTENERS.get(subscriber).onEvent(newEvent);
        }
    }

    private static void publishEventSingleThread(final Event newEvent) {
        final Runnable task = () -> {
            for (final String subscriber : SUBSCRIBER_IDS.get(newEvent.getEventName())) {
                LOGGER.debug(String.format("New event notification. Event name: %s | Event subscriber ID: %s", newEvent.getEventName(), subscriber));
                Thread.currentThread().setName(String.format("Event: %s | Listener: %s | Mode: %s", newEvent.getEventName(), subscriber, behavior.name()));
                LISTENERS.get(subscriber).onEvent(newEvent);
            }
        };
        threadPool.execute(task);
    }

    private static void publishEventMultiThreads(final Event newEvent) {
        for (final String subscriber : SUBSCRIBER_IDS.get(newEvent.getEventName())) {
            final Runnable task = () -> {
                LOGGER.debug(String.format("New event notification. Event name: %s | Event subscriber ID: %s", newEvent.getEventName(), subscriber));
                Thread.currentThread().setName(String.format("Event: %s | Listener: %s | Mode: %s", newEvent.getEventName(), subscriber, behavior.name()));
                LISTENERS.get(subscriber).onEvent(newEvent);
            };
            threadPool.execute(task);
        }
    }

    /**
     * To adjust the event bus behavior.
     *
     * @param behavior
     *            behavior to set. See {@link EventBusMode}
     */
    public static void configure(final EventBusMode behavior) {
        LOGGER.info("Event bus configuration set to: " + behavior);
        EventBus.behavior = behavior;
    }

}
