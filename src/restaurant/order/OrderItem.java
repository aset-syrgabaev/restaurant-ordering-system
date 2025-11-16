package restaurant.order;

import restaurant.menu.Meal;

import java.math.BigDecimal;

public record OrderItem(Meal meal, int quantity) {
    public OrderItem {
        if (meal == null) {
            throw new IllegalArgumentException("Meal must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    public BigDecimal getItemTotal() {
        return meal.getTotalPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
