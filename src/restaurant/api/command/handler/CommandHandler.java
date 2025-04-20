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

    // Общая логика обработки команд
    private <T extends Command> void handleCommand(T cmd, CommandAction<T> action) {
        try {
            action.execute(cmd);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке команды: " + e.getMessage());
        }
    }

    // Логика для каждой команды
    public void handle(CreateOrderCommand cmd) {
        handleCommand(cmd, c -> {
            CustomerOrder order = new CustomerOrder(c.orderId());
            repo.save(order);
            bus.publish(new OrderCreatedEvent(c.orderId()));
        });
    }

    public void handle(AddDishCommand cmd) {
        handleCommand(cmd, c -> {
            var order = get(c.orderId());
            order.addDish(c.dishName());
            repo.save(order);
            bus.publish(new DishAddedEvent(c.orderId(), c.dishName()));
        });
    }

    public void handle(PrepareDishCommand cmd) {
        handleCommand(cmd, c -> {
            var order = get(c.orderId());
            order.prepareDish(c.dishName());
            repo.save(order);
            bus.publish(new DishPreparedEvent(c.orderId(), c.dishName()));
        });
    }

    public void handle(CompleteOrderCommand cmd) {
        handleCommand(cmd, c -> {
            var order = get(c.orderId());
            order.completeOrder();
            repo.save(order);
            bus.publish(new OrderCompletedEvent(c.orderId()));
        });
    }

    public void handle(RemoveDishCommand cmd) {
        handleCommand(cmd, c -> {
            var order = get(c.orderId());
            order.removeDish(c.dishName());
            repo.save(order);
            bus.publish(new DishRemovedEvent(c.orderId(), c.dishName()));
        });
    }

    private CustomerOrder get(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Нет заказа с ID: " + id));
    }

    @FunctionalInterface
    public interface CommandAction<T> {
        void execute(T command) throws Exception;
    }
}