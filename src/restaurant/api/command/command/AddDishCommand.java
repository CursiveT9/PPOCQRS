package restaurant.api.command.command;

public record AddDishCommand(String orderId, String dishName) implements Command {
}