package restaurant.notification;

public interface OrderObserver {
    void onOrderEvent(OrderEvent event);
}
