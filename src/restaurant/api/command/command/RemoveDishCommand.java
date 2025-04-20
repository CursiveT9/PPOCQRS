package restaurant.api.command.command;

public record RemoveDishCommand(String orderId, String dishName) {}
