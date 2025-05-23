package restaurant.api.command.handler;

import restaurant.api.command.command.PrepareDishCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.DishPreparedEvent;
import restaurant.api.common.event.EventBus;

public class PrepareDishHandler implements CommandHandler<PrepareDishCommand> {
    private final OrderRepository repo;
    private final EventBus bus;

    public PrepareDishHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    @Override
    public void handle(PrepareDishCommand command) {
        var order = repo.findById(command.getOrderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.prepareDish(command.getDishName());
        repo.save(order);
        bus.publish(new DishPreparedEvent(command.getOrderId(), command.getDishName()));
    }
}
