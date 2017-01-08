package eu.daxiongmao.utils.eventbus;

public enum EventBusMode {

    /**
     * Event will be processed in the caller thread.<br>
     * ~ Keep the current thread<br>
     * ~ This is like a standard method call but you don't know who will do the job.<br>
     * Listeners will be notify 1 by 1 in the same thread, one after another. Be careful to NOT run a lot of computation!
     */
    NO_THREADS,

    /**
     * Process event in a single thread.<br>
     * ~ 1 event = 1 thread.<br>
     * Notify listeners 1 by 1, one after another, in a particular thread.
     */
    EVENT_THREAD,

    /**
     * Process event in multi-threads.<br>
     * This will create 1 thread per event consumer.<br>
     * ~ 1 event = 1 or N threads (as many threads as subscribers)
     */
    CONSUMER_THREAD,

}
