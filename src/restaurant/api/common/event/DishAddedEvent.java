package restaurant.api.common.event;

public record DishAddedEvent(String orderId, String dishName) implements Event {}