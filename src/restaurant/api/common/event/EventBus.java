package restaurant.api.common.event;

import java.util.*;

public class EventBus {
    private final List<EventListener> listeners = new ArrayList<>();

    public void register(EventListener l) {
        listeners.add(l);
    }

    public void publish(Event event) {
        for (EventListener l : listeners)
            l.handle(event);
    }

    public interface EventListener {
        void handle(Event event);
    }
}
