package restaurant.api.command.command;

public record CompleteOrderCommand(String orderId) implements Command {
}