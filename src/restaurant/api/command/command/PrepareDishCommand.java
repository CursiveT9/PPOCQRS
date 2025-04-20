package restaurant.api.command.command;

public record PrepareDishCommand(String orderId, String dishName) {}