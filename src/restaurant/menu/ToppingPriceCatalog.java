package restaurant.menu;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class ToppingPriceCatalog {
    private final Map<ToppingId, BigDecimal> prices;

    public ToppingPriceCatalog() {
        prices = new EnumMap<>(ToppingId.class);
        registerDefaultPrices();
    }

    public BigDecimal getPrice(ToppingId id) {
        BigDecimal price = prices.get(id);
        if (price == null) {
            throw new IllegalArgumentException("Unknown topping id: " + id);
        }
        return price;
    }

    private void registerDefaultPrices() {
        registerPrice(ToppingId.EXTRA_CHEESE, new BigDecimal("300"));
        registerPrice(ToppingId.EXTRA_SAUCE, new BigDecimal("200"));
        registerPrice(ToppingId.BACON, new BigDecimal("400"));
    }

    private void registerPrice(ToppingId id, BigDecimal price) {
        prices.put(id, price);
    }
}
