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

    // Обработка команды на создание заказа
    public void handle(CreateOrderCommand cmd) {
        try {
            CustomerOrder order = new CustomerOrder(cmd.orderId());
            repo.save(order);
            bus.publish(new OrderCreatedEvent(cmd.orderId()));
        } catch (Exception e) {
            System.err.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    // Обработка команды на добавление блюда
    public void handle(AddDishCommand cmd) {
        try {
            var order = get(cmd.orderId());
            order.addDish(cmd.dishName());
            repo.save(order);
            bus.publish(new DishAddedEvent(cmd.orderId(), cmd.dishName()));
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении блюда: " + e.getMessage());
        }
    }

    // Обработка команды на приготовление блюда
    public void handle(PrepareDishCommand cmd) {
        try {
            var order = get(cmd.orderId());
            order.prepareDish(cmd.dishName());
            repo.save(order);
            bus.publish(new DishPreparedEvent(cmd.orderId(), cmd.dishName()));
        } catch (Exception e) {
            System.err.println("Ошибка при приготовлении блюда: " + e.getMessage());
        }
    }

    // Обработка команды на завершение заказа
    public void handle(CompleteOrderCommand cmd) {
        try {
            var order = get(cmd.orderId());
            order.completeOrder();
            repo.save(order);
            bus.publish(new OrderCompletedEvent(cmd.orderId()));
        } catch (Exception e) {
            System.err.println("Ошибка при завершении заказа: " + e.getMessage());
        }
    }

    // Обработка команды на удаление блюда
    public void handle(RemoveDishCommand cmd) {
        try {
            var order = get(cmd.orderId());
            order.removeDish(cmd.dishName());
            repo.save(order);
            bus.publish(new DishRemovedEvent(cmd.orderId(), cmd.dishName()));
        } catch (Exception e) {
            System.err.println("Ошибка при удалении блюда: " + e.getMessage());
        }
    }

    // Получение заказа по ID
    private CustomerOrder get(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Нет заказа с ID: " + id));
    }
}
