package restaurant.api.command.handler;

import restaurant.api.command.command.CreateOrderCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.EventBus;
import restaurant.api.common.event.OrderCreatedEvent;

public class CreateOrderHandler implements CommandHandler<CreateOrderCommand> {
    private final OrderRepository repo;
    private final EventBus bus;

    public CreateOrderHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    @Override
    public void handle(CreateOrderCommand command) {
        var order = new CustomerOrder(command.getOrderId());
        repo.save(order);
        bus.publish(new OrderCreatedEvent(command.getOrderId()));
    }
}
