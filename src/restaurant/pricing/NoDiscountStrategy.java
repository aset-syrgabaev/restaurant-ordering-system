package restaurant.pricing;

import java.math.BigDecimal;

public class NoDiscountStrategy implements PricingStrategy {
    @Override
    public BigDecimal apply(BigDecimal baseTotal) {
        if (baseTotal == null) {
            throw new IllegalArgumentException("Base total must not be null");
        }
        return baseTotal;
    }
}
