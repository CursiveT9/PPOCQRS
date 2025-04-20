package restaurant.api;

import restaurant.api.command.handler.CommandHandler;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.EventBus;
import restaurant.api.console.ConsoleInterface;
import restaurant.api.query.repository.OrderViewRepository;
import restaurant.api.query.service.EventHandler;

public class RestaurantApplication {
    public static void main(String[] args) {
        var bus = new EventBus();
        var queryRepo = new OrderViewRepository();
        bus.register(new EventHandler(queryRepo));

        var commandRepo = new OrderRepository();
        var commandHandler = new CommandHandler(commandRepo, bus);

        new ConsoleInterface(commandHandler, queryRepo).run();
    }
}