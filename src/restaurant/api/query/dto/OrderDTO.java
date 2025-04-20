package restaurant.api.query.dto;

import java.util.List;

public record OrderDTO(
        String orderId,
        String status,
        List<DishDTO> dishes
) {}