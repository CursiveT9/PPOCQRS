package restaurant.api.command.handler;

import restaurant.api.command.command.PrepareDishCommand;
import restaurant.api.command.model.CustomerOrder;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.DishPreparedEvent;
import restaurant.api.common.event.EventBus;

public class PrepareDishHandler {

    private final OrderRepository repo;
    private final EventBus bus;

    public PrepareDishHandler(OrderRepository repo, EventBus bus) {
        this.repo = repo;
        this.bus = bus;
    }

    public void handle(PrepareDishCommand cmd) {
        try {
            // Находим заказ по ID
            var order = repo.findById(cmd.orderId())
                    .orElseThrow(() -> new RuntimeException("Заказ не найден"));

            // Подготовка блюда
            order.prepareDish(cmd.dishName());

            // Сохраняем изменения
            repo.save(order);

            // Публикуем событие, что блюдо готово
            bus.publish(new DishPreparedEvent(cmd.orderId(), cmd.dishName()));
        } catch (Exception e) {
            System.err.println("Ошибка при приготовлении блюда: " + e.getMessage());
        }
    }
}
