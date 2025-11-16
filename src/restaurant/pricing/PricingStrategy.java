package restaurant.pricing;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal apply(BigDecimal baseTotal);
}
