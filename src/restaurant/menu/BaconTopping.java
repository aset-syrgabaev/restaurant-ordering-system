package restaurant.menu;

public class BaconTopping extends ToppingDecorator {
    public BaconTopping(Meal baseMeal, ToppingPriceCatalog priceCatalog) {
        super(baseMeal, priceCatalog.getPrice(ToppingId.BACON), "bacon");
    }
}
