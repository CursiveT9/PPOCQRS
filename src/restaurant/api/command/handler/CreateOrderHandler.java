package restaurant.api.command.handler;

import restaurant.api.command.command.CreateOrderCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.OrderCreatedEvent;
import restaurant.api.common.event.EventBus;

public class CreateOrderHandler {

    private final OrderRepository repo;
    private final EventBus bus;

    public CreateOrderHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public void handle(CreateOrderCommand cmd) {
        try {
            // Создаем новый заказ
            CustomerOrder order = new CustomerOrder(cmd.orderId());
            repo.save(order);

            // Публикуем событие, что заказ был создан
            bus.publish(new OrderCreatedEvent(cmd.orderId()));
        } catch (Exception e) {
            System.err.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }
}
