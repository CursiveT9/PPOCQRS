package restaurant.api.query.service;

import restaurant.api.common.event.*;
import restaurant.api.query.model.DishView;
import restaurant.api.query.model.OrderView;
import restaurant.api.query.repository.OrderViewRepository;

public class EventHandler implements EventBus.EventListener {
    private final OrderViewRepository repo;

    public EventHandler(OrderViewRepository repo) {
        this.repo = repo;
    }

    public void handle(Event event) {
        if (event instanceof OrderCreatedEvent e) {
            repo.save(new OrderView(e.orderId()));
        } else if (event instanceof DishAddedEvent e) {
            repo.findById(e.orderId()).ifPresent(v -> v.dishes.add(new DishView(e.dishName())));
        } else if (event instanceof DishPreparedEvent e) {
            repo.findById(e.orderId()).ifPresent(v ->
                    v.dishes.stream()
                            .filter(d -> d.name.equals(e.dishName()))
                            .findFirst()
                            .ifPresent(d -> d.prepared = true)
            );
        } else if (event instanceof OrderCompletedEvent e) {
            repo.findById(e.orderId()).ifPresent(v -> v.status = "COMPLETED");
        } else if (event instanceof DishRemovedEvent e) {
            repo.findById(e.orderId()).ifPresent(v ->
                    v.dishes.removeIf(d -> d.name.equals(e.dishName()))
            );
        }
    }

}
