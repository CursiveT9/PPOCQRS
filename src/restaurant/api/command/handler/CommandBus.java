package restaurant.api.command.handler;

import restaurant.api.command.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandBus {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> handlers = new HashMap<>();

    public <T extends Command> void registerHandler(Class<T> type, CommandHandler<T> handler) {
        handlers.put(type, handler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Command> void handle(T command) {
        CommandHandler<T> handler = (CommandHandler<T>) handlers.get(command.getClass());
        if (handler == null) {
            throw new RuntimeException("Нет обработчика для команды: " + command.getClass().getSimpleName());
        }
        handler.handle(command);
    }
}
