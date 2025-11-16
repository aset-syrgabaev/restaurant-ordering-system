package restaurant.order;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private String id;
    private String tableNumber;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;
    private String pricingStrategyCode;

    public OrderBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public OrderBuilder forTable(String tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder withPricingStrategyCode(String pricingStrategyCode) {
        this.pricingStrategyCode = pricingStrategyCode;
        return this;
    }

    public Order build() {
        return new Order(id, tableNumber, items, status, pricingStrategyCode);
    }
}
