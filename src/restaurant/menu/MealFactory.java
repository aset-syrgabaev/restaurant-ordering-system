package restaurant.menu;

import java.math.BigDecimal;

public record MealFactory(MenuPriceCatalog priceCatalog) {
    public Meal createMeal(MealId id, MealSize size) {
        MealCategory category = mapCategory(id);
        String name = mapName(id);
        BigDecimal price = priceCatalog.getPrice(id, size);
        return new BaseMeal(name, category, size, price);
    }

    private MealCategory mapCategory(MealId id) {
        return switch (id) {
            case MARGHERITA_PIZZA, PEPPERONI_PIZZA -> MealCategory.PIZZA;
            case CHEESEBURGER, VEGGIE_BURGER -> MealCategory.BURGER;
            case COLA, ORANGE_JUICE -> MealCategory.DRINK;
        };
    }

    private String mapName(MealId id) {
        return switch (id) {
            case MARGHERITA_PIZZA -> "Margherita Pizza";
            case PEPPERONI_PIZZA -> "Pepperoni Pizza";
            case CHEESEBURGER -> "Cheeseburger";
            case VEGGIE_BURGER -> "Veggie Burger";
            case COLA -> "Cola";
            case ORANGE_JUICE -> "Orange Juice";
        };
    }
}
