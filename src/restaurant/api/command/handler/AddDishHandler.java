package restaurant.api.command.handler;

import restaurant.api.command.command.AddDishCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.DishAddedEvent;
import restaurant.api.common.event.EventBus;

public class AddDishHandler {
    private final OrderRepository repo;
    private final EventBus bus;

    public AddDishHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public void handle(AddDishCommand cmd) {
        var order = repo.findById(cmd.orderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.addDish(cmd.dishName());
        repo.save(order);
        bus.publish(new DishAddedEvent(cmd.orderId(), cmd.dishName()));
    }
}
