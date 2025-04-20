package restaurant.api.common.event;

public record DishPreparedEvent(String orderId, String dishName) implements Event {}