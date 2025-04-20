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
            switch (in.nextLine()) {
                case "1" -> {
                    System.out.print("ID заказа: ");
                    cmd.handle(new CreateOrderCommand(in.nextLine()));
                }
                case "2" -> {
                    System.out.print("ID: ");
                    String id = in.nextLine();
                    System.out.print("Блюдо: ");
                    cmd.handle(new AddDishCommand(id, in.nextLine()));
                }
                case "3" -> {
                    System.out.print("ID: ");
                    String id = in.nextLine();
                    System.out.print("Блюдо: ");
                    cmd.handle(new PrepareDishCommand(id, in.nextLine()));
                }
                case "4" -> {
                    System.out.print("ID: ");
                    cmd.handle(new CompleteOrderCommand(in.nextLine()));
                }
                case "5" -> query.findAll().forEach(this::printOrder);
                case "6" -> {
                    System.out.print("ID: ");
                    String id = in.nextLine();
                    System.out.print("Удалить блюдо: ");
                    cmd.handle(new RemoveDishCommand(id, in.nextLine()));
                }
                case "0" -> System.exit(0);
            }
        }
    }

    private void printOrder(OrderView o) {
        System.out.println("Заказ #" + o.orderId + " [" + o.status + "]");
        o.dishes.forEach(d -> System.out.println("- " + d.name + (d.prepared ? " (готово)" : "")));
    }
}