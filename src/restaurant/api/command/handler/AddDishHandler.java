package restaurant.api.command.handler;

import restaurant.api.command.command.AddDishCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.DishAddedEvent;
import restaurant.api.common.event.EventBus;

public class AddDishHandler implements CommandHandler<AddDishCommand> {
    private final OrderRepository repo;
    private final EventBus bus;

    public AddDishHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    @Override
    public void handle(AddDishCommand command) {
        var order = repo.findById(command.getOrderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.addDish(command.getDishName());
        repo.save(order);
        bus.publish(new DishAddedEvent(command.getOrderId(), command.getDishName()));
    }
}
