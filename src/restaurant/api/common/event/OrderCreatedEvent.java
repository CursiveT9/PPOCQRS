package restaurant.api.common.event;

public record OrderCreatedEvent(String orderId) implements Event {}
