package restaurant.api.common.event;

public record DishRemovedEvent(String orderId, String dishName) implements Event {}

