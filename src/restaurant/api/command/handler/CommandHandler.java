package restaurant.api.command.handler;
import restaurant.api.command.command.Command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
