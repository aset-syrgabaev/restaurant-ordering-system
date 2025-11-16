package restaurant.app;

import restaurant.facade.OrderSummary;
import restaurant.facade.RestaurantOrderingFacade;
import restaurant.menu.MealFactory;
import restaurant.menu.MealId;
import restaurant.menu.MealSize;
import restaurant.menu.MenuPriceCatalog;
import restaurant.menu.ToppingId;
import restaurant.menu.ToppingPriceCatalog;
import restaurant.notification.CustomerNotificationObserver;
import restaurant.notification.KitchenDisplayObserver;
import restaurant.notification.OrderEventPublisher;
import restaurant.order.Order;
import restaurant.order.OrderItem;
import restaurant.order.OrderRepository;
import restaurant.order.OrderStatus;
import restaurant.pricing.PricingStrategyFactory;
import restaurant.pricing.PricingStrategyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RestaurantOrderingFacade facade = createFacade();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Restaurant Ordering System started");

        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> handleCreateOrder(scanner, facade);
                case "2" -> handleAddMeal(scanner, facade);
                case "3" -> handleChangePricingStrategy(scanner, facade);
                case "4" -> handlePlaceOrder(scanner, facade);
                case "5" -> handleUpdateStatus(scanner, facade);
                case "6" -> handleShowOrderSummary(scanner, facade);
                case "7" -> handleListOrders(facade);
                case "0" -> running = false;
                default -> System.out.println("Unknown choice");
            }
        }

        System.out.println("Restaurant Ordering System closed");
    }

    private static RestaurantOrderingFacade createFacade() {
        MenuPriceCatalog priceCatalog = new MenuPriceCatalog();
        ToppingPriceCatalog toppingPriceCatalog = new ToppingPriceCatalog();
        MealFactory mealFactory = new MealFactory(priceCatalog);
        PricingStrategyFactory pricingStrategyFactory = new PricingStrategyFactory();
        OrderRepository orderRepository = new OrderRepository();
        OrderEventPublisher eventPublisher = new OrderEventPublisher();

        eventPublisher.registerObserver(new KitchenDisplayObserver());
        eventPublisher.registerObserver(new CustomerNotificationObserver());

        return new RestaurantOrderingFacade(
                mealFactory,
                toppingPriceCatalog,
                pricingStrategyFactory,
                orderRepository,
                eventPublisher
        );
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Choose action:");
        System.out.println("1. Create new order");
        System.out.println("2. Add meal to order");
        System.out.println("3. Change pricing strategy");
        System.out.println("4. Place order");
        System.out.println("5. Update order status");
        System.out.println("6. Show order summary");
        System.out.println("7. List all orders");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private static void handleCreateOrder(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter table number: ");
        String tableNumber = scanner.nextLine();
        PricingStrategyType strategyType = askPricingStrategy(scanner);
        String orderId = facade.createOrder(tableNumber, strategyType);
        System.out.println("Order created with id: " + orderId);
    }

    private static void handleAddMeal(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter order id: ");
        String orderId = scanner.nextLine();

        MealId mealId = askMealId(scanner);
        MealSize size = askMealSize(scanner);
        List<ToppingId> toppings = askToppings(scanner);
        int quantity = askQuantity(scanner);

        facade.addMealToOrder(orderId, mealId, size, toppings, quantity);
        System.out.println("Meal added to order " + orderId);
    }

    private static void handleChangePricingStrategy(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter order id: ");
        String orderId = scanner.nextLine();
        PricingStrategyType strategyType = askPricingStrategy(scanner);
        facade.changePricingStrategy(orderId, strategyType);
        System.out.println("Pricing strategy updated for order " + orderId);
    }

    private static void handlePlaceOrder(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter order id: ");
        String orderId = scanner.nextLine();
        facade.placeOrder(orderId);
    }

    private static void handleUpdateStatus(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter order id: ");
        String orderId = scanner.nextLine();
        OrderStatus status = askOrderStatus(scanner);
        facade.updateOrderStatus(orderId, status);
    }

    private static void handleShowOrderSummary(Scanner scanner, RestaurantOrderingFacade facade) {
        System.out.print("Enter order id: ");
        String orderId = scanner.nextLine();
        OrderSummary summary = facade.getOrderSummary(orderId);
        System.out.println("Order id: " + summary.orderId());
        System.out.println("Table: " + summary.tableNumber());
        System.out.println("Status: " + summary.status());
        System.out.println("Pricing strategy: " + summary.pricingStrategyCode());
        System.out.println("Base total: " + summary.baseTotal());
        System.out.println("Final total: " + summary.finalTotal());
    }

    private static void handleListOrders(RestaurantOrderingFacade facade) {
        List<Order> orders = facade.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders");
            return;
        }
        for (Order order : orders) {
            int totalQuantity = 0;
            for (OrderItem item : order.getItems()) {
                totalQuantity += item.quantity();
            }
            System.out.println(
                    "Order " + order.getId()
                            + " table " + order.getTableNumber()
                            + " status " + order.getStatus()
                            + " total items " + totalQuantity
            );
        }
    }

    private static PricingStrategyType askPricingStrategy(Scanner scanner) {
        while (true) {
            System.out.println("Choose pricing strategy:");
            System.out.println("1. No discount");
            System.out.println("2. Student discount");
            System.out.println("3. Weekend discount");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            try {
                return switch (choice) {
                    case "1" -> PricingStrategyType.NO_DISCOUNT;
                    case "2" -> PricingStrategyType.STUDENT_DISCOUNT;
                    case "3" -> PricingStrategyType.WEEKEND_DISCOUNT;
                    default -> throw new IllegalArgumentException();
                };
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown choice, try again");
            }
        }
    }

    private static MealId askMealId(Scanner scanner) {
        while (true) {
            System.out.println("Choose meal:");
            System.out.println("1. Margherita Pizza");
            System.out.println("2. Pepperoni Pizza");
            System.out.println("3. Cheeseburger");
            System.out.println("4. Veggie Burger");
            System.out.println("5. Cola");
            System.out.println("6. Orange Juice");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            try {
                return switch (choice) {
                    case "1" -> MealId.MARGHERITA_PIZZA;
                    case "2" -> MealId.PEPPERONI_PIZZA;
                    case "3" -> MealId.CHEESEBURGER;
                    case "4" -> MealId.VEGGIE_BURGER;
                    case "5" -> MealId.COLA;
                    case "6" -> MealId.ORANGE_JUICE;
                    default -> throw new IllegalArgumentException();
                };
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown choice, try again");
            }
        }
    }

    private static MealSize askMealSize(Scanner scanner) {
        while (true) {
            System.out.println("Choose size:");
            System.out.println("1. SMALL");
            System.out.println("2. MEDIUM");
            System.out.println("3. LARGE");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            try {
                return switch (choice) {
                    case "1" -> MealSize.SMALL;
                    case "2" -> MealSize.MEDIUM;
                    case "3" -> MealSize.LARGE;
                    default -> throw new IllegalArgumentException();
                };
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown choice, try again");
            }
        }
    }

    private static List<ToppingId> askToppings(Scanner scanner) {
        List<ToppingId> toppings = new ArrayList<>();
        boolean adding = true;
        while (adding) {
            System.out.println("Add topping?");
            System.out.println("1. Extra cheese");
            System.out.println("2. Extra sauce");
            System.out.println("3. Bacon");
            System.out.println("0. No more toppings");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> toppings.add(ToppingId.EXTRA_CHEESE);
                case "2" -> toppings.add(ToppingId.EXTRA_SAUCE);
                case "3" -> toppings.add(ToppingId.BACON);
                case "0" -> adding = false;
                default -> System.out.println("Unknown choice");
            }
        }
        return toppings;
    }

    private static int askQuantity(Scanner scanner) {
        System.out.print("Enter quantity: ");
        String value = scanner.nextLine();
        try {
            int quantity = Integer.parseInt(value);
            if (quantity <= 0) {
                return 1;
            }
            return quantity;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private static OrderStatus askOrderStatus(Scanner scanner) {
        while (true) {
            System.out.println("Choose new status:");
            System.out.println("1. IN_PREPARATION");
            System.out.println("2. READY");
            System.out.println("3. COMPLETED");
            System.out.println("4. CANCELLED");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            try {
                return switch (choice) {
                    case "1" -> OrderStatus.IN_PREPARATION;
                    case "2" -> OrderStatus.READY;
                    case "3" -> OrderStatus.COMPLETED;
                    case "4" -> OrderStatus.CANCELLED;
                    default -> throw new IllegalArgumentException();
                };
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown choice, try again");
            }
        }
    }
}
