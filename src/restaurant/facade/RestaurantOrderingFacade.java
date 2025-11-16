package restaurant.facade;

import restaurant.menu.BaconTopping;
import restaurant.menu.ExtraCheeseTopping;
import restaurant.menu.ExtraSauceTopping;
import restaurant.menu.Meal;
import restaurant.menu.MealFactory;
import restaurant.menu.MealId;
import restaurant.menu.MealSize;
import restaurant.menu.ToppingId;
import restaurant.menu.ToppingPriceCatalog;
import restaurant.notification.OrderEvent;
import restaurant.notification.OrderEventPublisher;
import restaurant.notification.OrderEventType;
import restaurant.order.Order;
import restaurant.order.OrderBuilder;
import restaurant.order.OrderItem;
import restaurant.order.OrderRepository;
import restaurant.order.OrderStatus;
import restaurant.pricing.PricingStrategy;
import restaurant.pricing.PricingStrategyFactory;
import restaurant.pricing.PricingStrategyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RestaurantOrderingFacade(
        MealFactory mealFactory,
        ToppingPriceCatalog toppingPriceCatalog,
        PricingStrategyFactory pricingStrategyFactory,
        OrderRepository orderRepository,
        OrderEventPublisher eventPublisher
) {
    public String createOrder(String tableNumber, PricingStrategyType strategyType) {
        String id = UUID.randomUUID().toString();
        OrderBuilder builder = new OrderBuilder()
                .withId(id)
                .forTable(tableNumber)
                .withStatus(OrderStatus.NEW)
                .withPricingStrategyCode(strategyType.name());
        Order order = builder.build();
        orderRepository.save(order);
        return id;
    }

    public void addMealToOrder(String orderId, MealId mealId, MealSize size, List<ToppingId> toppings, int quantity) {
        Order order = orderRepository.findById(orderId);
        Meal meal = mealFactory.createMeal(mealId, size);
        Meal decorated = applyToppings(meal, toppings);
        OrderItem item = new OrderItem(decorated, quantity);
        order.addItem(item);
        orderRepository.save(order);
    }

    private Meal applyToppings(Meal base, List<ToppingId> toppings) {
        Meal result = base;
        if (toppings == null) {
            return result;
        }
        for (ToppingId toppingId : toppings) {
            result = decorate(result, toppingId);
        }
        return result;
    }

    private Meal decorate(Meal meal, ToppingId toppingId) {
        return switch (toppingId) {
            case EXTRA_CHEESE -> new ExtraCheeseTopping(meal, toppingPriceCatalog);
            case EXTRA_SAUCE -> new ExtraSauceTopping(meal, toppingPriceCatalog);
            case BACON -> new BaconTopping(meal, toppingPriceCatalog);
        };
    }

    public void changePricingStrategy(String orderId, PricingStrategyType type) {
        Order order = orderRepository.findById(orderId);
        order.setPricingStrategyCode(type.name());
        orderRepository.save(order);
    }

    public void placeOrder(String orderId) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);
        OrderEvent event = new OrderEvent(orderId, order.getStatus(), OrderEventType.ORDER_PLACED);
        eventPublisher.publish(event);
    }

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(newStatus);
        orderRepository.save(order);
        OrderEvent event = new OrderEvent(orderId, order.getStatus(), OrderEventType.STATUS_CHANGED);
        eventPublisher.publish(event);
    }

    public OrderSummary getOrderSummary(String orderId) {
        Order order = orderRepository.findById(orderId);
        BigDecimal baseTotal = order.getBaseTotal();
        PricingStrategy strategy = pricingStrategyFactory.createByCode(order.getPricingStrategyCode());
        BigDecimal finalTotal = strategy.apply(baseTotal);
        return new OrderSummary(
                order.getId(),
                order.getTableNumber(),
                order.getStatus(),
                baseTotal,
                finalTotal,
                order.getPricingStrategyCode()
        );
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
