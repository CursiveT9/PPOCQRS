package restaurant.api.command.handler;

import restaurant.api.command.command.*;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.*;

public class CommandHandler {
    private final OrderRepository repo;
    private final EventBus bus;

    public CommandHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public void handle(CreateOrderCommand cmd) {
        CustomerOrder order = new CustomerOrder(cmd.orderId());
        repo.save(order);
        bus.publish(new OrderCreatedEvent(cmd.orderId()));
    }

    public void handle(AddDishCommand cmd) {
        var order = get(cmd.orderId());
        order.addDish(cmd.dishName());
        repo.save(order);
        bus.publish(new DishAddedEvent(cmd.orderId(), cmd.dishName()));
    }

    public void handle(PrepareDishCommand cmd) {
        var order = get(cmd.orderId());
        order.prepareDish(cmd.dishName());
        repo.save(order);
        bus.publish(new DishPreparedEvent(cmd.orderId(), cmd.dishName()));
    }

    public void handle(CompleteOrderCommand cmd) {
        var order = get(cmd.orderId());
        order.completeOrder();
        repo.save(order);
        bus.publish(new OrderCompletedEvent(cmd.orderId()));
    }

    public void handle(RemoveDishCommand cmd) {
        var order = get(cmd.orderId());
        order.removeDish(cmd.dishName());
        repo.save(order);
        bus.publish(new DishRemovedEvent(cmd.orderId(), cmd.dishName()));
    }

    private CustomerOrder get(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Нет заказа"));
    }
}
