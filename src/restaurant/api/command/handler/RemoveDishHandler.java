package restaurant.api.command.handler;

import restaurant.api.command.command.RemoveDishCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.DishRemovedEvent;
import restaurant.api.common.event.EventBus;

public class RemoveDishHandler implements CommandHandler<RemoveDishCommand> {
    private final OrderRepository repo;
    private final EventBus bus;

    public RemoveDishHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    @Override
    public void handle(RemoveDishCommand command) {
        var order = repo.findById(command.getOrderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.removeDish(command.getDishName());
        repo.save(order);
        bus.publish(new DishRemovedEvent(command.getOrderId(), command.getDishName()));
    }
}
