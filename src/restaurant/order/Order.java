package restaurant.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String id;
    private final String tableNumber;
    private final List<OrderItem> items;
    private OrderStatus status;
    private String pricingStrategyCode;

    Order(String id, String tableNumber, List<OrderItem> items, OrderStatus status, String pricingStrategyCode) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id must not be blank");
        }
        if (tableNumber == null || tableNumber.isBlank()) {
            throw new IllegalArgumentException("Table number must not be blank");
        }
        this.id = id;
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>(items);
        this.status = status;
        this.pricingStrategyCode = pricingStrategyCode;
    }

    public String getId() {
        return id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        this.status = status;
    }

    public String getPricingStrategyCode() {
        return pricingStrategyCode;
    }

    public void setPricingStrategyCode(String pricingStrategyCode) {
        this.pricingStrategyCode = pricingStrategyCode;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Order item must not be null");
        }
        items.add(item);
    }

    public BigDecimal getBaseTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getItemTotal());
        }
        return total;
    }
}
