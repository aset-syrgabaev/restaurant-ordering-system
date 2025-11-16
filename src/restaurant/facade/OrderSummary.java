package restaurant.facade;

import restaurant.order.OrderStatus;

import java.math.BigDecimal;

public record OrderSummary(
        String orderId,
        String tableNumber,
        OrderStatus status,
        BigDecimal baseTotal,
        BigDecimal finalTotal,
        String pricingStrategyCode
) {
}
