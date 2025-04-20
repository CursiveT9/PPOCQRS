package restaurant.api.console;

import restaurant.api.command.command.*;
import restaurant.api.command.handler.CommandHandler;
import restaurant.api.query.model.OrderView;
import restaurant.api.query.repository.OrderViewRepository;

import java.util.Scanner;

public class ConsoleInterface {
    private final CommandHandler cmd;
    private final OrderViewRepository query;
    private final Scanner in = new Scanner(System.in);

    public ConsoleInterface(CommandHandler cmd, OrderViewRepository query) {
        this.cmd = cmd;
        this.query = query;
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Новый заказ\n2. Добавить блюдо\n3. Приготовить\n4. Завершить\n5. Показать все\n6. Удалить блюдо\n0. Выход");
            String choice = in.nextLine();
            try {
                switch (choice) {
                    case "1" -> createOrder();
                    case "2" -> addDish();
                    case "3" -> prepareDish();
                    case "4" -> completeOrder();
                    case "5" -> showAllOrders();
                    case "6" -> removeDish();
                    case "0" -> System.exit(0);
                    default -> System.out.println("Некорректный выбор");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void createOrder() {
        System.out.print("ID заказа: ");
        String id = in.nextLine();
        cmd.handle(new CreateOrderCommand(id));
    }

    private void addDish() {
        System.out.print("ID: ");
        String id = in.nextLine();
        System.out.print("Блюдо: ");
        String dish = in.nextLine();
        cmd.handle(new AddDishCommand(id, dish));
    }

    private void prepareDish() {
        System.out.print("ID: ");
        String id = in.nextLine();
        System.out.print("Блюдо: ");
        String dish = in.nextLine();
        cmd.handle(new PrepareDishCommand(id, dish));
    }

    private void completeOrder() {
        System.out.print("ID: ");
        String id = in.nextLine();
        cmd.handle(new CompleteOrderCommand(id));
    }

    private void showAllOrders() {
        query.findAll().forEach(this::printOrder);
    }

    private void removeDish() {
        System.out.print("ID: ");
        String id = in.nextLine();
        System.out.print("Удалить блюдо: ");
        String dish = in.nextLine();
        cmd.handle(new RemoveDishCommand(id, dish));
    }

    private void printOrder(OrderView o) {
        System.out.println("Заказ #" + o.orderId + " [" + o.status + "]");
        o.dishes.forEach(d -> System.out.println("- " + d.name + (d.prepared ? " (готово)" : "")));
    }
}
