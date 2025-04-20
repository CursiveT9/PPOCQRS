package restaurant.api.command.model;

import java.util.*;

public class CustomerOrder {
    private final String orderId;
    private final List<DishItem> dishes = new ArrayList<>();
    private OrderStatus status;

    public CustomerOrder(String orderId) {
        this.orderId = orderId;
        this.status = OrderStatus.PLACED;
    }

    public void addDish(String name) {
        if (status != OrderStatus.PLACED)
            throw new IllegalStateException("Нельзя добавить блюдо после начала приготовления");
        dishes.add(new DishItem(name));
    }

    public void prepareDish(String name) {
        dishes.stream()
                .filter(d -> d.getName().equals(name) && !d.isPrepared())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"))
                .markPrepared();
    }

    public void completeOrder() {
        if (dishes.stream().anyMatch(d -> !d.isPrepared()))
            throw new IllegalStateException("Не все блюда готовы");
        this.status = OrderStatus.COMPLETED;
    }

    public void removeDish(String dishName) {
        if (status != OrderStatus.PLACED)
            throw new IllegalStateException("Нельзя менять заказ после начала приготовления");

        boolean removed = dishes.removeIf(d -> d.getName().equals(dishName) && !d.isPrepared());
        if (!removed) throw new IllegalArgumentException("Блюдо не найдено или уже готово");
    }


    public String getOrderId() { return orderId; }
    public List<DishItem> getDishes() { return dishes; }
    public OrderStatus getStatus() { return status; }
}