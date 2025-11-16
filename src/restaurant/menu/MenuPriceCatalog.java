package restaurant.menu;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class MenuPriceCatalog {
    private final Map<MealId, Map<MealSize, BigDecimal>> prices;

    public MenuPriceCatalog() {
        prices = new EnumMap<>(MealId.class);
        registerDefaultPrices();
    }

    public BigDecimal getPrice(MealId id, MealSize size) {
        Map<MealSize, BigDecimal> sizePrices = prices.get(id);
        if (sizePrices == null) {
            throw new IllegalArgumentException("Unknown meal id: " + id);
        }
        BigDecimal price = sizePrices.get(size);
        if (price == null) {
            throw new IllegalArgumentException("No price for size " + size + " of meal " + id);
        }
        return price;
    }

    private void registerDefaultPrices() {
        registerMeal(
                MealId.MARGHERITA_PIZZA,
                new BigDecimal("2000"),
                new BigDecimal("2500"),
                new BigDecimal("3000")
        );
        registerMeal(
                MealId.PEPPERONI_PIZZA,
                new BigDecimal("2300"),
                new BigDecimal("2800"),
                new BigDecimal("3300")
        );
        registerMeal(
                MealId.CHEESEBURGER,
                new BigDecimal("1800"),
                new BigDecimal("2200"),
                new BigDecimal("2600")
        );
        registerMeal(
                MealId.VEGGIE_BURGER,
                new BigDecimal("1700"),
                new BigDecimal("2100"),
                new BigDecimal("2500")
        );
        registerMeal(
                MealId.COLA,
                new BigDecimal("600"),
                new BigDecimal("700"),
                new BigDecimal("800")
        );
        registerMeal(
                MealId.ORANGE_JUICE,
                new BigDecimal("700"),
                new BigDecimal("800"),
                new BigDecimal("900")
        );
    }

    private void registerMeal(MealId id, BigDecimal small, BigDecimal medium, BigDecimal large) {
        Map<MealSize, BigDecimal> sizePrices = new EnumMap<>(MealSize.class);
        sizePrices.put(MealSize.SMALL, small);
        sizePrices.put(MealSize.MEDIUM, medium);
        sizePrices.put(MealSize.LARGE, large);
        prices.put(id, sizePrices);
    }
}
