package restaurant.api.command.model;

public class DishItem {
    private final String name;
    private boolean prepared = false;

    public DishItem(String name) {
        this.name = name;
    }

    public void markPrepared() {
        this.prepared = true;
    }

    public String getName() { return name; }
    public boolean isPrepared() { return prepared; }
}
