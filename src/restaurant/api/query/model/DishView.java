package restaurant.api.query.model;

public class DishView {
    public final String name;
    public boolean prepared = false;

    public DishView(String name) {
        this.name = name;
    }
}
