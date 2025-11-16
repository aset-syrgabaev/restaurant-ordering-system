package restaurant.menu;

public class ExtraSauceTopping extends ToppingDecorator {
    public ExtraSauceTopping(Meal baseMeal, ToppingPriceCatalog priceCatalog) {
        super(baseMeal, priceCatalog.getPrice(ToppingId.EXTRA_SAUCE), "extra sauce");
    }
}
