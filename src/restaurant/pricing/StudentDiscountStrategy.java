package restaurant.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentDiscountStrategy implements PricingStrategy {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal apply(BigDecimal baseTotal) {
        if (baseTotal == null) {
            throw new IllegalArgumentException("Base total must not be null");
        }
        BigDecimal discount = baseTotal.multiply(DISCOUNT_RATE);
        return baseTotal.subtract(discount).setScale(0, RoundingMode.HALF_UP);
    }
}
