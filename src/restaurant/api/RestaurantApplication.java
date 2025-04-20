package restaurant.api;

import restaurant.api.command.command.*;
import restaurant.api.command.handler.*;
import restaurant.api.command.repository.OrderRepository;
import restaurant.api.common.event.EventBus;
import restaurant.api.console.ConsoleInterface;
import restaurant.api.query.repository.OrderViewRepository;
import restaurant.api.query.service.EventHandler;
import restaurant.api.query.service.OrderQueryService;

public class RestaurantApplication {
    public static void main(String[] args) {
        // Инициализация EventBus
        var bus = new EventBus();

        // Инициализация репозиториев
        var commandRepo = new OrderRepository();
        var queryRepo = new OrderViewRepository();

        // Регистрируем обработчик событий
        bus.register(new EventHandler(queryRepo)); // <-- Добавьте эту строку

        var commandBus = new CommandBus();

        // регистрируем обработчики
        commandBus.registerHandler(CreateOrderCommand.class, new CreateOrderHandler(commandRepo, bus));
        commandBus.registerHandler(AddDishCommand.class, new AddDishHandler(commandRepo, bus));
        commandBus.registerHandler(PrepareDishCommand.class, new PrepareDishHandler(commandRepo, bus));
        commandBus.registerHandler(RemoveDishCommand.class, new RemoveDishHandler(commandRepo, bus));
        commandBus.registerHandler(CompleteOrderCommand.class, new CompleteOrderHandler(commandRepo, bus));

        // Создание сервиса запросов
        var queryService = new OrderQueryService(queryRepo);

        // Запуск консольного интерфейса
        new ConsoleInterface(commandBus, queryRepo, queryService).run();
    }
}