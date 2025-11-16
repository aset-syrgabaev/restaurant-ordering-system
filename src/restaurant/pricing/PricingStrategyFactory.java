package restaurant.pricing;

public class PricingStrategyFactory {
    public PricingStrategy create(PricingStrategyType type) {
        if (type == null) {
            throw new IllegalArgumentException("Pricing strategy type must not be null");
        }
        return switch (type) {
            case NO_DISCOUNT -> new NoDiscountStrategy();
            case STUDENT_DISCOUNT -> new StudentDiscountStrategy();
            case WEEKEND_DISCOUNT -> new WeekendDiscountStrategy();
        };
    }

    public PricingStrategy createByCode(String code) {
        if (code == null || code.isBlank()) {
            return create(PricingStrategyType.NO_DISCOUNT);
        }
        PricingStrategyType type = PricingStrategyType.valueOf(code);
        return create(type);
    }
}
