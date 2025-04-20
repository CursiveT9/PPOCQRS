package restaurant.api.command.handler;

import restaurant.api.command.command.Command;
import restaurant.api.command.command.*;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.EventBus;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommandBus {
    private final Map<Class<? extends Command>, Consumer<Command>> handlers = new HashMap<>();

    public CommandBus() {
        // Регистрация обработчиков для каждой команды
        register(CreateOrderCommand.class, (cmd) -> new CreateOrderHandler(new OrderRepository(), new EventBus()).handle((CreateOrderCommand) cmd));
        register(PrepareDishCommand.class, (cmd) -> new PrepareDishHandler(new OrderRepository(), new EventBus()).handle((PrepareDishCommand) cmd));
        register(AddDishCommand.class, (cmd) -> new AddDishHandler(new OrderRepository(), new EventBus()).handle((AddDishCommand) cmd));
        register(CompleteOrderCommand.class, (cmd) -> new CompleteOrderHandler(new OrderRepository(), new EventBus()).handle((CompleteOrderCommand) cmd));
        register(RemoveDishCommand.class, (cmd) -> new CommandHandler(new OrderRepository(), new EventBus()).handle((RemoveDishCommand) cmd));
    }

    public <T extends Command> void register(Class<T> commandType, Consumer<T> handler) {
        handlers.put(commandType, (Consumer<Command>) handler);
    }

    public <T extends Command> void dispatch(T command) {
        Consumer<Command> handler = handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("Не найден обработчик для команды: " + command.getClass());
        }
        handler.accept(command);
    }
}