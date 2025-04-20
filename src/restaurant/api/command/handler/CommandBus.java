package restaurant.api.command.handler;

import restaurant.api.command.command.Command;
import restaurant.api.command.command.CreateOrderCommand;
import restaurant.api.command.command.PrepareDishCommand;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.EventBus;

import java.util.HashMap;
import java.util.Map;

public class CommandBus {

    private final Map<Class<? extends Command>, Object> handlers = new HashMap<>();

    public CommandBus() {
        // Регистрация обработчиков
        handlers.put(CreateOrderCommand.class, new CreateOrderHandler(new OrderRepository(), new EventBus()));
        handlers.put(PrepareDishCommand.class, new PrepareDishHandler(new OrderRepository(), new EventBus()));
        // Добавьте другие обработчики по мере необходимости
    }

    public <T extends Command> void dispatch(T command) {
        Object handler = handlers.get(command.getClass());

        if (handler == null) {
            throw new IllegalArgumentException("Не найден обработчик для команды: " + command.getClass());
        }

        try {
            if (handler instanceof CreateOrderHandler) {
                ((CreateOrderHandler) handler).handle((CreateOrderCommand) command);
            } else if (handler instanceof PrepareDishHandler) {
                ((PrepareDishHandler) handler).handle((PrepareDishCommand) command);
            }
            // Добавьте обработку для других команд
        } catch (Exception e) {
            System.err.println("Ошибка при обработке команды: " + e.getMessage());
        }
    }
}
