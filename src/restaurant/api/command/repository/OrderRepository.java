package restaurant.api.command.repository;

import restaurant.api.command.model.CustomerOrder;

import java.util.*;

public class OrderRepository {
    private final Map<String, CustomerOrder> orders = new HashMap<>();

    public void save(CustomerOrder order) {
        orders.put(order.getOrderId(), order);
    }

    public Optional<CustomerOrder> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<CustomerOrder> findAll() {
        return new ArrayList<>(orders.values());
    }
}