package restaurant.api.command.handler;

import restaurant.api.command.command.CompleteOrderCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.OrderCompletedEvent;
import restaurant.api.common.event.EventBus;

public class CompleteOrderHandler implements CommandHandler<CompleteOrderCommand> {
    private final OrderRepository repo;
    private final EventBus bus;

    public CompleteOrderHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    @Override
    public void handle(CompleteOrderCommand command) {
        var order = repo.findById(command.getOrderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.completeOrder();
        repo.save(order);
        bus.publish(new OrderCompletedEvent(command.getOrderId()));
    }
}
