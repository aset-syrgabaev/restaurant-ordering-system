package restaurant.menu;

public class ExtraCheeseTopping extends ToppingDecorator {
    public ExtraCheeseTopping(Meal baseMeal, ToppingPriceCatalog priceCatalog) {
        super(baseMeal, priceCatalog.getPrice(ToppingId.EXTRA_CHEESE), "extra cheese");
    }
}
