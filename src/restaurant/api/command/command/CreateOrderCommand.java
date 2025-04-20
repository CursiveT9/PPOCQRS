package restaurant.api.command.command;

import java.util.UUID;

public class CreateOrderCommand implements Command {
    private final String commandId;
    private final String orderId;

    public CreateOrderCommand(String orderId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        System.out.println("CreateOrderCommand: " + orderId);
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    public String getOrderId() {
        return orderId;
    }
}
