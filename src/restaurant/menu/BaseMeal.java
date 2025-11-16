package restaurant.menu;

import java.math.BigDecimal;

public class BaseMeal extends Meal {
    public BaseMeal(String name, MealCategory category, MealSize size, BigDecimal basePrice) {
        super(name, category, size, basePrice);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getBasePrice();
    }
}
