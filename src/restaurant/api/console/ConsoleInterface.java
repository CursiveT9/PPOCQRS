package restaurant.api.console;

import restaurant.api.command.command.*;
import restaurant.api.command.handler.CommandBus;
import restaurant.api.query.dto.OrderStatsDTO;
import restaurant.api.query.repository.OrderViewRepository;
import restaurant.api.query.service.OrderQueryService;
import restaurant.api.query.model.OrderView;

import java.util.Scanner;

public class ConsoleInterface {
    private final CommandBus commandBus;
    private final OrderViewRepository queryRepo;
    private final OrderQueryService queryService;
    private final Scanner in = new Scanner(System.in);

    public ConsoleInterface(CommandBus commandBus, OrderViewRepository queryRepo, OrderQueryService queryService) {
        this.commandBus = commandBus;
        this.queryRepo = queryRepo;
        this.queryService = queryService;
    }

    public void run() {
        while (true) {
            System.out.println("""
                    \n1. Новый заказ
                    2. Добавить блюдо
                    3. Приготовить
                    4. Завершить
                    5. Показать все
                    6. Удалить блюдо
                    7. Статистика
                    0. Выход
                    """);
            String choice = in.nextLine();
            try {
                switch (choice) {
                    case "1" -> createOrder();
                    case "2" -> addDish();
                    case "3" -> prepareDish();
                    case "4" -> completeOrder();
                    case "5" -> showAllOrders();
                    case "6" -> removeDish();
                    case "7" -> showStatistics();
                    case "0" -> System.exit(0);
                    default -> System.out.println("Некорректный выбор");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void showStatistics() {
        OrderStatsDTO stats = queryService.getStatistics();
        System.out.printf("""
            Всего заказов: %d
            Завершено: %d
            Популярные блюда: %s
            """, stats.totalOrders(), stats.completedOrders(), stats.dishesPopularity());
    }

    private void createOrder() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        commandBus.handle(new CreateOrderCommand(id));
        System.out.println("Заказ #" + id + " создан");
        showAllOrders();
    }

    private void addDish() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        System.out.print("Блюдо: ");
        String dish = in.nextLine();
        commandBus.handle(new AddDishCommand(id, dish));
    }

    private void prepareDish() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        System.out.print("Блюдо: ");
        String dish = in.nextLine();
        commandBus.handle(new PrepareDishCommand(id, dish));
    }

    private void completeOrder() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        commandBus.handle(new CompleteOrderCommand(id));
    }

    private void showAllOrders() {
        System.out.println("\nВсе заказы:");
        queryRepo.findAll().forEach(this::printOrder);
    }

    private void removeDish() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        System.out.print("Удалить блюдо: ");
        String dish = in.nextLine();
        commandBus.handle(new RemoveDishCommand(id, dish));
    }

    private void printOrder(OrderView o) {
        System.out.println("\nЗаказ #" + o.orderId + " [" + o.status + "]");
        o.dishes.forEach(d -> System.out.println("- " + d.name + (d.prepared ? " (готово)" : "")));
    }
}
