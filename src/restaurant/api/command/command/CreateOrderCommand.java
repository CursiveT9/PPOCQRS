package restaurant.api.command.command;

public record CreateOrderCommand(String orderId) implements Command {
}