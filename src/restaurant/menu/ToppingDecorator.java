package restaurant.menu;

import java.math.BigDecimal;

public abstract class ToppingDecorator extends Meal {
    private final Meal baseMeal;
    private final BigDecimal toppingPrice;
    private final String toppingName;

    protected ToppingDecorator(Meal baseMeal, BigDecimal toppingPrice, String toppingName) {
        super(baseMeal.getName(), baseMeal.getCategory(), baseMeal.getSize(), baseMeal.getBasePrice());
        this.baseMeal = baseMeal;
        this.toppingPrice = toppingPrice;
        this.toppingName = toppingName;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return baseMeal.getTotalPrice().add(toppingPrice);
    }

    @Override
    public String getName() {
        return baseMeal.getName() + " + " + toppingName;
    }
}
