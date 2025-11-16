package restaurant.menu;

import java.math.BigDecimal;

public abstract class Meal {
    private final String name;
    private final MealCategory category;
    private final MealSize size;
    private final BigDecimal basePrice;

    protected Meal(String name, MealCategory category, MealSize size, BigDecimal basePrice) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public MealCategory getCategory() {
        return category;
    }

    public MealSize getSize() {
        return size;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public abstract BigDecimal getTotalPrice();
}
