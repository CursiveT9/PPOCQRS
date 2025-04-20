package restaurant.api.query.repository;

import restaurant.api.query.model.OrderView;

import java.util.*;

public class OrderViewRepository {
    private final Map<String, OrderView> orders = new HashMap<>();

    public void save(OrderView view) {
        orders.put(view.orderId, view);
    }

    public Optional<OrderView> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<OrderView> findAll() {
        return new ArrayList<>(orders.values());
    }
}
