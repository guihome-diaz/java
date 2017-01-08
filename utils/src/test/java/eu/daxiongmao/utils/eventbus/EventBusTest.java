package eu.daxiongmao.utils.eventbus;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class EventBusTest {

    private void registerEvent(final String eventName, final DummyEventListener listener) {
        final String listenerUuid = EventBus.subscribeToEvent(eventName, listener);
        listener.registerNewEventListernerUUID(eventName, listenerUuid);
    }

    @Test
    public void basicNotificationTest() {
        // Given
        final String eventName = "green";
        final DummyEventListener listener = new DummyEventListener("Dummy 1");
        registerEvent(eventName, listener);

        // Business
        final Event event = new Event(eventName);
        EventBus.configure(EventBusMode.NO_THREADS);
        EventBus.publishEvent(event);

        // Assert
        Assert.assertEquals(1, listener.getCounterForEvent(eventName).intValue());
    }

    @Test
    public void basicNotificationTest_withParameters() {
        // Given
        final String eventName = "green";
        final DummyEventListener listener = new DummyEventListener("Dummy 1");
        registerEvent(eventName, listener);

        // Business
        final Event event = new Event(eventName);
        event.addProperty("author", "Guillaume");
        event.addProperty("version", 1);
        EventBus.configure(EventBusMode.NO_THREADS);
        EventBus.publishEvent(event);

        // Assert
        Assert.assertEquals(1, listener.getCounterForEvent(eventName).intValue());
        Assert.assertNotNull(listener.getLatestEventReceived());
        Assert.assertEquals(2, listener.getLatestEventReceived().getProperties().size());
        Assert.assertEquals("Guillaume", listener.getLatestEventReceived().getProperty("author"));
        Assert.assertEquals(1, (int) listener.getLatestEventReceived().getProperty("version"));
    }

    @Test
    public void basicMultiNotificationsTest_Sync() {
        // Given
        final String eventName = "blue";
        final DummyEventListener listener1 = new DummyEventListener("Dummy 11");
        final DummyEventListener listener2 = new DummyEventListener("Dummy 12");
        final DummyEventListener listener3 = new DummyEventListener("Dummy 13");
        registerEvent(eventName, listener1);
        registerEvent(eventName, listener2);
        registerEvent(eventName, listener3);

        // Business
        final Event event = new Event(eventName);
        EventBus.configure(EventBusMode.NO_THREADS);
        EventBus.publishEvent(event);

        // Assert
        Assert.assertEquals(1, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());
    }

    @Test
    public void basicMultiNotificationsTest_Concurrent() throws InterruptedException {
        // Given
        final String eventName = "orange";
        final DummyEventListener listener1 = new DummyEventListener("Dummy 21");
        final DummyEventListener listener2 = new DummyEventListener("Dummy 22");
        final DummyEventListener listener3 = new DummyEventListener("Dummy 23");
        registerEvent(eventName, listener1);
        registerEvent(eventName, listener2);
        registerEvent(eventName, listener3);

        // Business
        final Event event = new Event(eventName);
        EventBus.configure(EventBusMode.CONSUMER_THREAD);
        EventBus.publishEvent(event);

        // Assert
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(1, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());
    }

    @Test
    public void unsubscriberTest_multiThread() throws InterruptedException {
        // Given
        final String eventName = "red";
        final DummyEventListener listener1 = new DummyEventListener("Dummy 21");
        final DummyEventListener listener2 = new DummyEventListener("Dummy 22");
        final DummyEventListener listener3 = new DummyEventListener("Dummy 23");
        registerEvent(eventName, listener1);
        registerEvent(eventName, listener2);
        registerEvent(eventName, listener3);

        // ######### ADD SUBCRIPTIONS ##########

        // Business
        final Event event = new Event(eventName);
        EventBus.configure(EventBusMode.CONSUMER_THREAD);
        EventBus.publishEvent(event);

        // Assert
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(1, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());

        // ######### REMOVE EVENT SUBCRIPTION ##########

        // Business
        EventBus.unsubscribeToEvent(listener3.getListernerUuidForEvent(eventName));
        listener3.removeEventListernerUUID(listener3.getListernerUuidForEvent(eventName));
        EventBus.publishEvent(event);

        // Assert
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(2, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(2, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());
    }

    @Test
    public void unsubscriberTest_singleThread() throws InterruptedException {
        // Given
        final String eventName = "red";
        final DummyEventListener listener1 = new DummyEventListener("Dummy 21");
        final DummyEventListener listener2 = new DummyEventListener("Dummy 22");
        final DummyEventListener listener3 = new DummyEventListener("Dummy 23");
        registerEvent(eventName, listener1);
        registerEvent(eventName, listener2);
        registerEvent(eventName, listener3);

        // ######### ADD SUBCRIPTIONS ##########

        // Business
        final Event event = new Event(eventName);
        EventBus.configure(EventBusMode.EVENT_THREAD);
        EventBus.publishEvent(event);

        // Assert
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(1, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());

        // ######### REMOVE EVENT SUBCRIPTION ##########

        // Business
        EventBus.unsubscribeToEvent(listener3.getListernerUuidForEvent(eventName));
        listener3.removeEventListernerUUID(listener3.getListernerUuidForEvent(eventName));
        EventBus.publishEvent(event);

        // Assert
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(2, listener1.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(2, listener2.getCounterForEvent(eventName).intValue());
        Assert.assertEquals(1, listener3.getCounterForEvent(eventName).intValue());
    }
}
