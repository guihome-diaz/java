package eu.daxiongmao.utils.eventbus;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DummyEventListener implements EventListener {

    private final String listenerName;

    private Map<String, String> listenerUUIDs = new ConcurrentHashMap<>();

    private Map<String, AtomicInteger> notificationCounter = new ConcurrentHashMap<>();
    private Event latestEventReceived = null;

    public DummyEventListener(final String listernerName) {
        listenerName = listernerName;
    }

    @Override
    public void onEvent(final Event event) {
        System.out.println(String.format("%s # new notification for event: %s", listenerName, event));
        // Counters
        if (notificationCounter.containsKey(event.getEventName())) {
            final AtomicInteger counter = notificationCounter.get(event.getEventName());
            counter.incrementAndGet();
        } else {
            final AtomicInteger counter = new AtomicInteger(1);
            notificationCounter.put(event.getEventName(), counter);
        }
        // History
        latestEventReceived = event;
    }

    /**
     * returns the number of times a particular event occurred.
     *
     * @param eventName
     *            search event name
     * @return number of times that particular event occurred |or| NULL.
     */
    public Integer getCounterForEvent(final String eventName) {
        if (notificationCounter.containsKey(eventName)) {
            return notificationCounter.get(eventName).get();
        } else {
            return null;
        }
    }

    /**
     * To add a listenerUuid registration
     *
     * @param eventName
     *            event name
     * @param listenerUuid
     *            listener uuid
     */
    public void registerNewEventListernerUUID(final String eventName, final String listenerUuid) {
        listenerUUIDs.put(eventName, listenerUuid);
    }

    /**
     * To remove a listenerUuid registration
     *
     * @param listenerUuid
     *            listener uuid
     */
    public void removeEventListernerUUID(final String listenerUuid) {
        for (final Entry<String, String> listener : listenerUUIDs.entrySet()) {
            if (listener.getValue().equals(listenerUuid)) {
                listenerUUIDs.remove(listener.getKey());
            }
        }
    }

    /**
     * @return the listenerUuid corresponding to the given name |or| NULL
     */
    public String getListernerUuidForEvent(final String eventName) {
        String uuid = null;
        for (final Entry<String, String> evt : listenerUUIDs.entrySet()) {
            if (evt.getKey().equals(eventName)) {
                uuid = evt.getValue();
            }
        }
        return uuid;
    }

    /**
     * @return the latest event received.
     */
    public Event getLatestEventReceived() {
        return latestEventReceived;
    }
}
