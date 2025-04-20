package restaurant.api.query.service;

import restaurant.api.query.dto.DishDTO;
import restaurant.api.query.dto.OrderDTO;
import restaurant.api.query.dto.OrderStatsDTO;
import restaurant.api.query.model.OrderView;
import restaurant.api.query.repository.OrderViewRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderQueryService {
    private final OrderViewRepository repo;

    public OrderQueryService(OrderViewRepository repo) {
        this.repo = repo;
    }

    public List<OrderDTO> getOrderHistory() {
        return repo.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public OrderStatsDTO getStatistics() {
        var orders = repo.findAll();

        Map<String, Long> dishesPopularity = orders.stream()
                .flatMap(o -> o.dishes.stream())
                .collect(Collectors.groupingBy(d -> d.name, Collectors.counting()));

        return new OrderStatsDTO(
                orders.size(),
                orders.stream().filter(o -> "COMPLETED".equals(o.status)).count(),
                dishesPopularity
        );
    }

    private OrderDTO convertToDTO(OrderView view) {
        return new OrderDTO(
                view.orderId,
                view.status,
                view.dishes.stream()
                        .map(d -> new DishDTO(d.name, d.prepared))
                        .toList()
        );
    }
}