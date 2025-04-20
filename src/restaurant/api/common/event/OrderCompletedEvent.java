package restaurant.api.common.event;

public record OrderCompletedEvent(String orderId) implements Event {}