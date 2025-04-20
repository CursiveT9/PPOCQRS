package restaurant.api.query.dto;

import java.util.Map;

public record OrderStatsDTO(
        long totalOrders,
        long completedOrders,
        Map<String, Long> dishesPopularity
) {
    // Дополнительный конструктор для удобства
    public OrderStatsDTO(long totalOrders, long completedOrders) {
        this(totalOrders, completedOrders, Map.of());
    }
}