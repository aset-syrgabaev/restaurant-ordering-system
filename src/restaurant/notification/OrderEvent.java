package restaurant.notification;

import restaurant.order.OrderStatus;

public record OrderEvent(
        String orderId,
        OrderStatus status,
        OrderEventType type
) {
}
