package restaurant.api.command.command;

import java.util.UUID;

public class AddDishCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final String dishName;

    public AddDishCommand(String orderId, String dishName) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.dishName = dishName;
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDishName() {
        return dishName;
    }
}