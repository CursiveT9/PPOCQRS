package restaurant.api.query.model;

import java.util.ArrayList;
import java.util.List;

public class OrderView {
    public final String orderId;
    public final List<DishView> dishes = new ArrayList<>();
    public String status = "PLACED";

    public OrderView(String orderId) {
        this.orderId = orderId;
    }
}